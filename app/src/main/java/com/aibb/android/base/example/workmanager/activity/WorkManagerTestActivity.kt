package com.aibb.android.base.example.workmanager.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aibb.android.base.example.R
import com.aibb.android.base.example.workmanager.viewmodel.WorkManagerViewModel
import kotlinx.android.synthetic.main.workmanager_test_activity.*

class WorkManagerTestActivity : AppCompatActivity() {

    val viewModel: WorkManagerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workmanager_test_activity)
        initView()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        workerResult.height?.also {
            scrollView.scrollTo(0, it)
        }
    }

    private fun initView() {
        viewModel.initLocalBroadcastReceiver(this)

        val workObserver = Observer<String> {
            workerResult.text = it
        }
        viewModel.workerResult.observe(this, workObserver)

        // one time works
        oneTimeWorkerBtn.setOnClickListener {
            viewModel.startOneTimeWork()
        }

        //one time work after delete all
        oneTimeAfterDeleteAllWorkerBtn.setOnClickListener {
            viewModel.startOneTimeAfterDeleteAllWork()
        }

        //one time delay work
        oneTimeDelayWorkerBtn.setOnClickListener {
            viewModel.startOneTimeWorkDelay5Seconds()
        }

        //observe worker
        observeWorkerBtn.setOnClickListener {
            viewModel.observeWorkState()
        }

        //observe worker execute progress
        progressWorkerBtn.setOnClickListener {
            viewModel.observeWorkProgress()
        }

        //Periodic worker
        periodicWorkerBtn.setOnClickListener {
            viewModel.periodicWorker()
        }
    }
}