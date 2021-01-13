package com.aibb.android.base.example.workmanager.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.*
import com.aibb.android.base.example.MainApplication
import com.aibb.android.base.example.workmanager.contants.WorkerConstants
import com.aibb.android.base.example.workmanager.pojo.WorkEntity
import com.aibb.android.base.example.workmanager.worker.DeleteAllWorker
import com.aibb.android.base.example.workmanager.worker.OneTimeWorker
import com.aibb.android.base.example.workmanager.worker.PeriodicWorker
import com.aibb.android.base.example.workmanager.worker.ProgressWorker
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class WorkManagerViewModel : ViewModel() {

    companion object {
        const val TAG = "WorkManagerViewModel"
    }

    lateinit var context: Context

    private fun queryAllWorkFlow() =
        MainApplication.getInstance().database.workerDao().queryAllWorkFlow()

    private fun insertAllWorker(worker: WorkEntity) =
        MainApplication.getInstance().database.workerDao().insertAllWorker(worker)

    private fun deleteWorker(worker: WorkEntity) =
        MainApplication.getInstance().database.workerDao().delete(worker)

    val workerResult: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val localBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                WorkerConstants.LOCAL_BROADCAST_ACTION_WORKER_RESULT -> {
                    val data = intent?.getStringExtra("data")
                    workerResult.postValue(data)
                }
            }
        }
    }

    fun initLocalBroadcastReceiver(context: Context) {
        this.context = context
        val intentFilter = IntentFilter()
        intentFilter.addAction(WorkerConstants.LOCAL_BROADCAST_ACTION_WORKER_RESULT)
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(localBroadcastReceiver, intentFilter)
    }


    fun startOneTimeWork() {
        workerResult.postValue("开始执行startOneTimeWork")
//        val myWorkRequest = OneTimeWorkRequest.from(OneTimeWorker::class.java)

        val constraintsBuilder = Constraints.Builder()
//        constraintsBuilder.setRequiresCharging(true)
        val oneTimeWorkRequestBuilder =
            OneTimeWorkRequestBuilder<OneTimeWorker>().setConstraints(constraintsBuilder.build())
                .addTag("OneTimeWork")
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequestBuilder.build())
    }

    fun startOneTimeAfterDeleteAllWork() {
        workerResult.postValue("开始执行startOneTimeAfterDeleteAllWork")
        val myWorkRequest = OneTimeWorkRequest.from(OneTimeWorker::class.java)
        val deleteAllWorkRequest = OneTimeWorkRequest.from(DeleteAllWorker::class.java)
        WorkManager.getInstance(context)
            .beginWith(deleteAllWorkRequest)
            .then(myWorkRequest)
            .enqueue()
    }

    fun startOneTimeWorkDelay5Seconds() {
        workerResult.postValue("倒计时5秒开始执行startOneTimeWork")

        val oneTimeWorkRequestBuilder =
            OneTimeWorkRequestBuilder<OneTimeWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .addTag("OneTimeWorkDelay5Sencods")
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequestBuilder.build())

        viewModelScope.launch(Dispatchers.IO) {
            flow<Int> {
                (5 downTo 1).forEach {
                    emit(it)
                    delay(1000)
                }
            }.collect {
                workerResult.postValue("${it}秒开始执行startOneTimeWork")
            }
        }
    }

    fun observeWorkState() {
        workerResult.postValue("开始执行observeWork")

        val oneTimeWorkRequestBuilder =
            OneTimeWorkRequestBuilder<OneTimeWorker>()
                .addTag("observeWork")
        val worker = oneTimeWorkRequestBuilder.build()

        WorkManager.getInstance(context).getWorkInfoByIdLiveData(worker.id)
            .observe(context as AppCompatActivity) { workInfo ->
                if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
                    ToastUtils.showShort("observeWork execute success")
                }
            }
        WorkManager.getInstance(context).enqueue(worker)
    }

    fun observeWorkProgress() {
        workerResult.postValue("开始执行observeWorkProgress")
        val workRequestBuilder =
            OneTimeWorkRequestBuilder<ProgressWorker>()
                .setInitialDelay(1, TimeUnit.SECONDS)
        val worker = workRequestBuilder.build()
        WorkManager.getInstance(context).getWorkInfoByIdLiveData(worker.id)
            .observe(context as AppCompatActivity) { workerInfo ->
                workerInfo?.also {
                    val progress = it.progress
                    workerResult.postValue("执行observeWorkProgress进度：%${progress.getInt("Progress", 0 )}")
                }
            }
        WorkManager.getInstance(context).enqueue(worker)
    }

    fun periodicWorker() {
        workerResult.postValue("开始执行periodicWorker")
        val workRequestBuilder = PeriodicWorkRequestBuilder<PeriodicWorker>(5, TimeUnit.SECONDS)
        WorkManager.getInstance(context).enqueue(workRequestBuilder.build())
    }
}