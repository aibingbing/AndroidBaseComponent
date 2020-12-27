package com.aibb.android.base.example.workmanager.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.Worker
import androidx.work.WorkerParameters
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

class OneTimeWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    companion object {
        const val TAG = "OneTimeWorker"
    }

    override fun doWork(): Result {
        Log.i(TAG, "OneTimeWorker start ")
        insertWorkers()
        Log.i(TAG, "OneTimeWorker end ")
        return Result.success()
    }

    private fun insertWorkers() {
        // 插入5条数据
        GlobalScope.launch(Dispatchers.IO) {
            flow<Int> {
                List(5) {
                    emit(it)
                    delay(1000)
                }
            }.flowOn(Dispatchers.IO)
                .onCompletion {
                    Log.i(WorkManagerViewModel.TAG, "startOneTimeWork onCompletion")
                }
                .collect {
                    val work = WorkEntity(it, "Work$it", "work$it")

                    Log.i(WorkManagerViewModel.TAG, "insertWork: WorkEntity$it")
                    val intent = Intent(WorkerConstants.LOCAL_BROADCAST_ACTION_WORKER_RESULT)
                    intent.putExtra("data", "insertWork: WorkEntity$it")
                    LocalBroadcastManager.getInstance(applicationContext)
                        .sendBroadcast(intent)

                    MainApplication.getInstance().database.workerDao().insertAllWorker(work)
                }
        }
    }
}