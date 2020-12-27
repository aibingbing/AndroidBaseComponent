package com.aibb.android.base.example.workmanager.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.aibb.android.base.example.MainApplication
import com.aibb.android.base.example.workmanager.contants.WorkerConstants
import com.aibb.android.base.example.workmanager.pojo.WorkEntity
import com.aibb.android.base.example.workmanager.viewmodel.WorkManagerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class ProgressWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TAG = "ProgressWorker"
        const val Progress = "Progress"
    }

    override suspend fun doWork(): Result {
        Log.i(TAG, "ProgressWorker start ")
        insertWorkers()
        Log.i(TAG, "ProgressWorker end ")
        return Result.success()
    }

    private suspend fun insertWorkers() {
        val startUpdate = workDataOf(Progress to 0)
        val endUpdate = workDataOf(Progress to 100)
        setProgress(startUpdate)
        // 插入100条数据
        GlobalScope.launch(Dispatchers.IO) {
            flow<Int> {
                List(100) {
                    emit(it)
                    delay(1000)
                }
            }.flowOn(Dispatchers.IO)
                .onCompletion {
                    Log.i(WorkManagerViewModel.TAG, "ProgressWorker onCompletion")
                }
                .collect {
                    val work = WorkEntity(it, "Work$it", "work$it")

                    Log.i(TAG, "insertWork: WorkEntity$it")
                    val intent = Intent(WorkerConstants.LOCAL_BROADCAST_ACTION_WORKER_RESULT)
                    intent.putExtra("data", "insertWork: WorkEntity$it")
                    LocalBroadcastManager.getInstance(applicationContext)
                        .sendBroadcast(intent)

                    MainApplication.getInstance().database.workerDao().insertAllWorker(work)

                    setProgress(workDataOf(Progress to it))
                }
        }.join()
        setProgress(endUpdate)
    }
}