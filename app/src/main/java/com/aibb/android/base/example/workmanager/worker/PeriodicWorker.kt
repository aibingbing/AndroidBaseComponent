package com.aibb.android.base.example.workmanager.worker

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aibb.android.base.example.workmanager.contants.WorkerConstants
import com.blankj.utilcode.utils.NetworkUtils
import java.text.SimpleDateFormat
import java.util.*

class PeriodicWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {

    companion object {
        const val TAG = "PeriodicWorker"
    }

    override suspend fun doWork(): Result {
        val isAvailableByPing = NetworkUtils.isAvailableByPing(applicationContext)
        val intent = Intent(WorkerConstants.LOCAL_BROADCAST_ACTION_WORKER_RESULT)
        intent.putExtra(
            "data",
            "${SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())}当前网络是否可用：$isAvailableByPing"
        )
        LocalBroadcastManager.getInstance(applicationContext)
            .sendBroadcast(intent)
        return Result.success()
    }
}