package com.aibb.android.base.example.permission.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aibb.android.base.example.R
import com.blankj.utilcode.utils.ToastUtils
import com.qw.soul.permission.SoulPermission
import com.qw.soul.permission.bean.Permission
import com.qw.soul.permission.bean.Special
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.permission_request_test_activity.*
import permissions.dispatcher.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


//@RuntimePermissions
class PermissionRequestTestActivity : AppCompatActivity() {

    companion object {
        const val TAG = "PermissionRequestTestActivity"
        const val RC_CAMERA_AND_LOCATION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permission_request_test_activity)
        initView()
    }

    private fun initView() {
        requestPermissionByEasyPermissionBtn.setOnClickListener {
            requestPermissionByEasyPermission()
        }
        requestPermissionByRxPermissionBtn.setOnClickListener {
            requestPermissionByRxPermission()
        }
        requestPermissionByPermissionsDispatcherBtn.setOnClickListener {
            requestPermissionByPermissionsDispatcher()
        }
        requestPermissionBySoulPermission.setOnClickListener {
            requestPermissionBySoulPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private fun showAlertDialog(msg: String) {
        AlertDialog.Builder(this).setTitle("提示").setMessage(msg).show()
    }


    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private fun easyPermissionMethodRequiresTwoPermission() {
        val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            showAlertDialog("已经获取Camera & FineLocation 权限")
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this@PermissionRequestTestActivity,
                "请同意获取Camera & FineLocation权限",
                RC_CAMERA_AND_LOCATION,
                *perms
            )
        }
    }

    private fun requestPermissionByEasyPermission() {
        Log.i(TAG, "requestPermissionByEasyPermission")
        val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
        EasyPermissions.requestPermissions(
            this@PermissionRequestTestActivity,
            "请同意获取Camera & FineLocation权限",
            RC_CAMERA_AND_LOCATION,
            *perms
        )
    }

    private fun requestPermissionByRxPermission() {
        Log.i(TAG, "requestPermissionByRxPermission")
        ToastUtils.showShortToast(this, "需要RxJava3, 此项目是RxJava2, 暂不测试")
        // needs RxJava3
        val rxPermission = RxPermissions(this)
        /*
        rxPermission.request(Manifest.permission.CAMERA)
            .subscribe(granted -> {
            if (granted) { // Always true pre-M
                // I can control the camera now
            } else {
                // Oups permission denied
            }
        });
        */

        /*
        // Must be done during an initialization phase like onCreate
        RxView.clicks(findViewById(R.id.enableCamera))
            .compose(rxPermissions.ensure(Manifest.permission.CAMERA))
            .subscribe { granted -> }
         */

        /*
        rxPermissions.request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE
            ).subscribe { granted ->
                if (granted) {
                    // All requested permissions are granted
                } else {
                    // At least one permission is denied
                }
            }
         */
        /*
        rxPermissions
            .requestEach(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE
            )
            .subscribe { permission ->  // will emit 2 Permission objects
                if (permission.granted) {
                    // `permission.name` is granted !
                } else if (permission.shouldShowRequestPermissionRationale) {
                    // Denied permission without ask never again
                } else {
                    // Denied permission with ask never again
                    // Need to go to the settings
                }
            }
         */
    }

    //    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun requestPermissionByPermissionsDispatcher() {
        Log.i(TAG, "requestPermissionByPermissionsDispatcher")
        ToastUtils.showLongToast(this, "PermissionDispatcher 的RuntimePermissions注解加了后打包报错, 不太好用")
//        ToastUtils.showLongToast(this, "请求定位权限")
    }

//    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
//    fun showRationaleForLocationPermissionsDispatcher(request: PermissionRequest) {
//        showAlertDialog("请给APP定位权限来完成此功能")
//    }
//
//    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
//    fun onLocationDeniedPermissionsDispatcher() {
//        ToastUtils.showLongToast(this, "请求定位权限: Deny")
//    }
//
//    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
//    fun onLocationNeverAskAgainPermissionsDispatcher() {
//        ToastUtils.showLongToast(this, "请求定位权限：Never ask again")
//    }

    private fun requestPermissionBySoulPermission() {
        Log.i(TAG, "requestPermissionBySoulPermission")
        SoulPermission.getInstance().checkAndRequestPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,  //if you want do noting or no need all the callbacks you may use SimplePermissionAdapter instead
            object : CheckRequestPermissionListener {
                override fun onPermissionOk(permission: Permission) {
                    ToastUtils.showLongToast(
                        this@PermissionRequestTestActivity,
                        "${permission.toString()} is ok , you can do your operations"
                    )
                }

                override fun onPermissionDenied(permission: Permission) {
                    if (permission.shouldRationale()) {
                        ToastUtils.showLongToast(
                            this@PermissionRequestTestActivity,
                            "${permission.toString()} you should show a explain for user then retry "
                        )
                    } else {
                        ToastUtils.showLongToast(
                            this@PermissionRequestTestActivity,
                            "${permission.toString()} is refused you can not do next things"
                        )
                        SoulPermission.getInstance()
                            .goApplicationSettings { //if you need to know when back from app detail
                                ToastUtils.showLongToast(
                                    this@PermissionRequestTestActivity,
                                    "back from go appDetail"
                                )
                            }
                    }
                }
            })

        //  一次请求多个权限
//        SoulPermission.getInstance().checkAndRequestPermissions(
//            Permissions.build(
//                Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ),  //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
//            object : CheckRequestPermissionsListener {
//                override fun onAllPermissionOk(allPermissions: Array<Permission>) {
//                    ToastUtils.showLongToast(
//                        this@PermissionRequestTestActivity,
//                        "${allPermissions.size} permissions is ok you can do your operations"
//                    )
//                }
//
//                override fun onPermissionDenied(refusedPermissions: Array<Permission>) {
//                    ToastUtils.showLongToast(
//                        this@PermissionRequestTestActivity,
//                        "${refusedPermissions[0]} is refused you can not do next things"
//                    )
//                }
//            })

        // 检查某项权限
        val checkResult = SoulPermission.getInstance()
            .checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION)
        Log.i(TAG, "Check Manifest.permission.ACCESS_FINE_LOCATION Result: $checkResult")
        ToastUtils.showLongToast(
            this@PermissionRequestTestActivity,
            "Check Manifest.permission.ACCESS_FINE_LOCATION Result: $checkResult"
        )

        // 检查特殊权限[通知权限]
        val checkSpecialResultNotification = SoulPermission.getInstance().checkSpecialPermission(
            Special.NOTIFICATION
        )
        val checkSpecialResultUnKnownAppSources = SoulPermission.getInstance().checkSpecialPermission(
            Special.UNKNOWN_APP_SOURCES
        )
        val checkSpecialResultWriteSettings = SoulPermission.getInstance().checkSpecialPermission(
            Special.WRITE_SETTINGS
        )

        Log.i(
            TAG,
            "Check Special Permission Result: [NOTIFICATION: $checkSpecialResultNotification], UNKNOWN_APP_SOURCES= $checkSpecialResultUnKnownAppSources, WRITE_SETTINGS: $checkSpecialResultWriteSettings"
        )
        ToastUtils.showLongToast(
            this@PermissionRequestTestActivity,
            "Check Special Permission Result: [NOTIFICATION: $checkSpecialResultNotification], UNKNOWN_APP_SOURCES= $checkSpecialResultUnKnownAppSources, WRITE_SETTINGS: $checkSpecialResultWriteSettings"
        )

    }
}