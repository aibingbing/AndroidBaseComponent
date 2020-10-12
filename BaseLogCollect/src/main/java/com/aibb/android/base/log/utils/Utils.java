package com.aibb.android.base.log.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import java.io.FileInputStream;
import java.util.List;
import java.util.Locale;

/**
 * Author: aibingbing <br>
 * Date: 2019-09-12 <br>
 * Desc: <br>
 * Edit History: <br>
 */
public class Utils {

    private static final String TAG = "LogCollect.Utils";
    private static String processName = null;

    public static boolean isInMainProcess(Context context) {
        String mainProcessName = null;
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo != null) {
            mainProcessName = applicationInfo.processName;
        }
        if (isNullOrNil(mainProcessName)) {
            mainProcessName = context.getPackageName();
        }
        String processName = getProcessName(context);
        if (processName == null || processName.length() == 0) {
            processName = "";
        }

        return mainProcessName.equals(processName);
    }

    public static String getProcessName(final Context context) {
        if (processName != null) {
            return processName;
        }
        //will not null
        processName = getProcessNameInternal(context);
        return processName;
    }

    private static String getProcessNameInternal(final Context context) {
        int myPid = android.os.Process.myPid();

        if (context == null || myPid <= 0) {
            return "";
        }

        ActivityManager.RunningAppProcessInfo myProcess = null;
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null) {
            try {
                List<ActivityManager.RunningAppProcessInfo> appProcessList = activityManager
                        .getRunningAppProcesses();

                if (appProcessList != null) {
                    for (ActivityManager.RunningAppProcessInfo process : appProcessList) {
                        if (process.pid == myPid) {
                            myProcess = process;
                            break;
                        }
                    }

                    if (myProcess != null) {
                        return myProcess.processName;
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "getProcessNameInternal exception:" + e.getMessage());
            }
        }

        byte[] b = new byte[128];
        FileInputStream in = null;
        try {
            in = new FileInputStream("/proc/" + myPid + "/cmdline");
            int len = in.read(b);
            if (len > 0) {
                for (int i = 0; i < len; i++) { // lots of '0' in tail , remove them
                    if ((((int) b[i]) & 0xFF) > 128 || b[i] <= 0) {
                        len = i;
                        break;
                    }
                }
                return new String(b, 0, len);
            }

        } catch (Exception e) {
            Log.e(TAG, "getProcessNameInternal exception:" + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }

        return "";
    }


    private static boolean isNullOrNil(final String object) {
        if ((object == null) || (object.length() <= 0)) {
            return true;
        }
        return false;
    }

    public static String getDeviceInfo() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return String.format(
                "%1$s, %2$s;  %3$s, %4$s;  %5$s",
                Build.MANUFACTURER,
                model,
                Build.VERSION.RELEASE,
                Build.VERSION.SDK_INT,
                Locale.getDefault().getLanguage()
        );
    }
}

