package com.aibb.android.base.example.base

import android.content.Context
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.aibb.android.base.example.R
import com.aibb.android.base.mvp.BaseMvpActivity
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Copyright:   Copyright (c)  All rights reserved.<br>
 * Author:      aibingbing <br>
 * Date:        2020/8/29 <br>
 * Desc:        <br>
 */
abstract class MyBaseMvpActivity : BaseMvpActivity(), LifecycleProvider<ActivityEvent> {

    lateinit var mContext: Context
    var mDialog: MaterialDialog? = null

    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    override fun lifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T : Any?> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return bindUntilEvent(ActivityEvent.DESTROY)
    }

    fun showLoadingDialog() {
        if (!isFinishing) {
            showLoadingDialog(R.string.hint_data_loading)
        }
    }

    fun showLoadingDialog(strId: Int) {
        if (!isFinishing) {
            checkedDialog()
            mDialog = MaterialDialog(mContext)
                .cancelable(false)
                .cancelOnTouchOutside(false)
                .message(strId)
            mDialog?.show()
        }
    }

    fun dismissDialog() {
        if (!isFinishing) {
            checkedDialog()
        }
    }

    protected open fun checkedDialog() {
        if (mDialog?.isShowing == true) {
            mDialog?.dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        lifecycleSubject.onNext(ActivityEvent.CREATE)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    override fun onPause() {
        super.onPause()
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleSubject.onNext(ActivityEvent.STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
    }
}