package com.aibb.android.base.example.workmanager.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.aibb.android.base.example.MainApplication
import com.aibb.android.base.example.workmanager.contants.WorkerConstants
import com.aibb.android.base.example.workmanager.viewmodel.WorkManagerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DeleteAllWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    companion object {
        const val TAG = "DeleteAllWorker"
    }

    override fun doWork(): Result {
        Log.i(TAG, "DeleteAllWorker start ")
        deleteAllWorkers()
        Log.i(TAG, "DeleteAllWorker end ")
        return Result.success()
    }

    private fun deleteAllWorkers() {
        GlobalScope.launch(Dispatchers.IO) {
            Log.i(WorkManagerViewModel.TAG, "deleteAllWorkers")

            val intent = Intent(WorkerConstants.LOCAL_BROADCAST_ACTION_WORKER_RESULT)
            intent.putExtra("data", "deleteAllWorkers")
            LocalBroadcastManager.getInstance(applicationContext)
                .sendBroadcast(intent)

            MainApplication.getInstance().database.workerDao().deleteAllWork()
            delay(300)
        }
    }
}