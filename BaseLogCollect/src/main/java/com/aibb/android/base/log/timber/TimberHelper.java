package com.aibb.android.base.log.timber;

import android.content.Context;
import android.os.Environment;

import com.aibb.android.base.log.LogCollect;
import com.aibb.android.base.log.utils.Utils;
import com.aibb.android.base.log.xlog.XLogHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.mars.xlog.Xlog;

import java.io.File;

import timber.log.Timber;

/**
 * Author: aibingbing <br>
 * Date: 2019-09-12 <br>
 * Desc: <br>
 * Edit History: <br>
 */
public final class TimberHelper {
    private static boolean openFileLog;
    private static boolean openConsoleLog;


    private TimberHelper() {
    }

    public static void init(
            Context context,
            boolean openFileLog,
            boolean openConsoleLog,
            int logFileLevel,
            long fileMaxAliveTime
    ) {
        boolean isMainProcess = Utils.isInMainProcess(context);
        //只记录主进程的日志到文件
        TimberHelper.openFileLog = isMainProcess && openFileLog;
//        TimberHelper.openFileLog = openFileLog;
        TimberHelper.openConsoleLog = openConsoleLog;
        //init xlog
        if (TimberHelper.openFileLog) {
            XLogHelper.init(
                    context,
                    getLogDir(context),
                    getLogCacheDir(context),
                    transformLogLevel(logFileLevel),
                    fileMaxAliveTime
            );
        }

        //init Timber
        if (TimberHelper.openConsoleLog) {
            //init Logger
            FormatStrategy formatStrategy = PrettyFormatStrategy
                    .newBuilder()
                    .showThreadInfo(true)
                    .methodCount(2)
                    .methodOffset(2)
                    .build();
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
            Timber.plant(new DebugLogTree());
        } else {
            Timber.plant(new ReleaseLogTree());
        }
    }

    /**
     * 将MELog的日志级别转换成xlog的日志级别，
     * xlog的定义和其他日志框架不太一样
     *
     * @param level
     * @return
     */
    private static int transformLogLevel(int level) {
        switch (level) {
            case LogCollect.VERBOSE:
                return Xlog.LEVEL_VERBOSE;
            case LogCollect.DEBUG:
                return Xlog.LEVEL_DEBUG;
            case LogCollect.WARN:
                return Xlog.LEVEL_WARNING;
            case LogCollect.ERROR:
                return Xlog.LEVEL_ERROR;
            default:
                return Xlog.LEVEL_INFO;
        }
    }

    public static void flushLogFile(boolean isSync) {
        if (openFileLog) {
            XLogHelper.flush(isSync);
        }
    }

    public static void closeLogFile() {
        if (openFileLog) {
            XLogHelper.close();
        }
    }

    /**
     * 获取实际存储日志的文件夹（可能因为权限问题，存储在内部存储目录）
     *
     * @param context
     */
    public static File getLogUploadDir(Context context) {
        File logDir = getLogDir(context);
        File[] logDirFiles = logDir.listFiles();
        if (logDirFiles != null && logDirFiles.length > 0) {
            return logDir;
        }
        File logCacheDir = getLogCacheDir(context);
        File[] logCacheDirFiles = logCacheDir.listFiles();
        if (logCacheDirFiles != null && logCacheDirFiles.length > 0) {
            return logCacheDir;
        }
        return null;
    }

    /**
     * 通过能否在外部存储创建空文件夹来判断是否有外部存储权限
     *
     * @param context
     * @return
     */
    public static boolean hasExternalStoragePermission(Context context) {
        File dir = null;
        try {
            dir = new File(getLogDir(context).getParent(), "test");
            return dir.mkdirs() && dir.listFiles() != null;
        } catch (Exception e) {
            LogCollect.e(e.getMessage());
        } finally {
            if (dir != null) {
                Utils.deleteDir(dir);
            }
        }
        return false;
    }

    /**
     * 应用标准外部存储目录
     * /storage/emulated/0/package/xlog
     *
     * @param context
     * @return
     */
    private static File getLogDir(Context context) {
        File dir = new File(
                Environment.getExternalStorageDirectory(),
                Utils.getProcessName(context)
        );
        return new File(dir, "xlog");
    }

    /**
     * 应用标准内部存储目录
     * /data/data/package/files
     *
     * @param context
     * @return
     */
    private static File getLogCacheDir(Context context) {
        File dir = context.getFilesDir();
        return new File(dir, "xlog");
    }

    private static class DebugLogTree extends Timber.DebugTree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (openConsoleLog) {
                switch (priority) {
                    case android.util.Log.VERBOSE:
                        Logger.v(tag, message);
                        break;
                    case android.util.Log.DEBUG:
                        Logger.d(tag, message);
                        break;
                    case android.util.Log.INFO:
                        Logger.i(tag, message);
                        break;
                    case android.util.Log.WARN:
                        Logger.w(tag, message);
                        break;
                    case android.util.Log.ERROR:
                        Logger.e(tag, message);
                        break;
                    default:
                        break;
                }
            }

            if (openFileLog) {
                XLogHelper.log(priority, tag, message);
            }
        }
    }

    private static class ReleaseLogTree extends Timber.DebugTree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (openFileLog) {
                XLogHelper.log(priority, tag, message);
            }
        }
    }
}
