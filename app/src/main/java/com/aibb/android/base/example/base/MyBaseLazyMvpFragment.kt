package com.aibb.android.base.example.base

import com.afollestad.materialdialogs.MaterialDialog
import com.aibb.android.base.example.R
import com.aibb.android.base.mvp.BaseLazyMvpFragment

abstract class MyBaseLazyMvpFragment : BaseLazyMvpFragment() {
    var mDialog: MaterialDialog? = null

    fun isFinishing(): Boolean {
        return activity == null || activity?.isFinishing == true
    }

    fun showLoadingDialog() {
        if (!isFinishing()) {
            showLoadingDialog(R.string.hint_data_loading)
        }
    }

    fun showLoadingDialog(strId: Int) {
        if (!isFinishing()) {
            checkedDialog()
            mDialog = MaterialDialog(requireActivity())
                .cancelable(false)
                .cancelOnTouchOutside(false)
                .message(strId)
            mDialog?.show()
        }
    }

    fun dismissDialog() {
        if (!isFinishing()) {
            checkedDialog()
        }
    }

    protected open fun checkedDialog() {
        if (mDialog?.isShowing == true) {
            mDialog?.dismiss()
        }
    }
}