package com.aibb.android.base.log;

import android.content.Context;

import com.aibb.android.base.log.timber.TimberHelper;
import com.aibb.android.base.log.utils.Utils;
import com.blankj.utilcode.utils.AppUtils;
import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.SDCardUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.ZipUtils;

import java.io.File;
import java.util.Arrays;

import timber.log.Timber;

/**
 * Author: aibingbing <br>
 * Date: 2019-09-12 <br>
 * Desc: <br>
 * Edit History: <br>
 */
public final class LogCollect {

    public static final String TAG = LogCollect.class.getSimpleName();

    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;


    private static boolean isUploadingFile = false;
    private static int logLevel = INFO;
    private static boolean fileLogOpen = true;
    private static boolean consoleLogOpen = false;
    //duration alive seconds , default 7 days
    private static long fileLogMaxAliveTime = 7 * 24 * 60 * 60;
//    private static long fileLogMaxFileSize = 10 * 1024 *1024;

    private LogCollect() {
    }


    public static Config newConfig() {
        return new Config();
    }

    /**
     * 需要在启动时申请WRITE_EXTERNAL_STORAGE权限
     *
     * @param context
     */
    public static void init(Context context, Config config) {
        logLevel = config.logLevel;
        fileLogOpen = config.fileLogOpen;
        consoleLogOpen = config.consoleLogOpen;
        fileLogMaxAliveTime = config.fileLogMaxAliveTime;
        TimberHelper.init(context, fileLogOpen, consoleLogOpen, logLevel, fileLogMaxAliveTime);
    }

    public static void flushLog(boolean isSync) {
        TimberHelper.flushLogFile(isSync);
    }

    public static void closeLog() {
        TimberHelper.closeLogFile();
    }

    public static void log(int priority, String tag, String message, Throwable throwable) {
        Timber.log(priority, tag, message, throwable);
    }

    public static void v(String message, Object... args) {
        Timber.v(message, args);
    }

    public static void d(String message, Object... args) {
        Timber.d(message, args);
    }

    public static void i(String message, Object... args) {
        Timber.i(message, args);
    }

    public static void w(String message, Object... args) {
        Timber.w(message, args);
    }

    public static void e(String message, Object... args) {
        Timber.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        Timber.e(throwable, message, args);
    }

    /**
     * 上传文件日志
     *
     * @param context        上下文
     * @param taskId         本次上传任务的id,方便后续区分消息,eg:parent_release_etutest_afj3ll3jlj3lll3
     * @param zipFileName    日志文件zip后的文件名字
     * @param messageHandler 发送上传过程中消息处理
     * @param fileHandler    实际处理上传文件处理
     */
    public synchronized static void uploadFileLog(
            Context context,
            final String taskId,
            final String zipFileName,
            final UploadMessageHandler messageHandler,
            UploadFileHandler fileHandler
    ) {
        if (isUploadingFile) {
            return;
        }
        try {
            isUploadingFile = true;
            logAndSendMsg(
                    taskId,
                    messageHandler,
                    "[Info]开始上传日志任务"
                            + "\n[Info]App信息："
                            + AppUtils.getAppVersionName(context)
                            + ", "
                            + AppUtils.getAppVersionCode(context)
                            + "\n[Info]设备信息："
                            + Utils.getDeviceInfo()
                            + "\n[Info]外部存储信息：\n"
                            + SDCardUtils.getSDCardInfo()
                            + "\npermission:"
                            + TimberHelper.hasExternalStoragePermission(context)
            );
            //缓冲日志写入文件
            flushLog(true);
            if (StringUtils.isEmpty(taskId)) {
                logAndSendMsg(taskId, messageHandler, "[Error]任务失败，taskId为空");
                return;
            }
            if (StringUtils.isEmpty(zipFileName)) {
                logAndSendMsg(taskId, messageHandler, "[Error]任务失败，zipFileName为空");
                return;
            }
            if (!fileLogOpen) {
                logAndSendMsg(taskId, messageHandler, "[Error]任务失败，当前配置未开启记录到文件");
                return;
            }
            File logUploadDir = TimberHelper.getLogUploadDir(context);
            if (logUploadDir == null) {
                logAndSendMsg(taskId, messageHandler, "[Error]任务失败，获取日志存储目录失败");
                return;
            }
            File zipFileDir = new File(logUploadDir.getParent(), "zip");
            final File zipFile = new File(zipFileDir, zipFileName + ".zip");
            boolean createZipFile = FileUtils.createFileByDeleteOldFile(zipFile);
            if (!createZipFile) {
                logAndSendMsg(taskId, messageHandler, "[Error]任务失败，创建zip文件失败");
                return;
            }
            boolean zipResult = ZipUtils.zipFiles(Arrays.asList(logUploadDir.listFiles()), zipFile);
            if (!zipResult) {
                logAndSendMsg(taskId, messageHandler, "[Error]任务失败，压缩日志文件到zip文件失败");
                return;
            }
            fileHandler.uploadLogFile(zipFile.getAbsolutePath(), new UploadCompletionHandler() {
                @Override
                public void complete(String fileId) {
                    if (StringUtils.isEmpty(fileId)) {
                        logAndSendMsg(taskId, messageHandler, "[Error]任务失败，上传日志过程中获取fileId失败");
                    } else {
                        logAndSendMsg(
                                taskId,
                                messageHandler,
                                "[Info]上传日志压缩文件成功\nfileId-->[" + fileId + "]" +
                                        "\nzipFilePath-->[" + zipFile.getAbsolutePath() + "]"
                        );
                        //删除zip文件
                        if (zipFile != null) {
                            zipFile.delete();
                        }
                    }
                }
            });
        } catch (Exception e) {
            logAndSendMsg(taskId, messageHandler, "[Error]任务失败，错误信息：" + e.getLocalizedMessage());
        } finally {
            isUploadingFile = false;
        }
    }

    private static void logAndSendMsg(
            String taskId,
            UploadMessageHandler messageHandler,
            String msg
    ) {
        e(msg);
        messageHandler.sendMsg(taskId, msg);
    }

    public static String getObjectPackageName(Object object) {
        Package p = object.getClass().getPackage();
        if (p == null) {
            return object.getClass().getSimpleName();
        }
        return String.format(
                "%1$s.%2$s",
                p.getName(),
                object.getClass().getSimpleName()
        );
    }

    public interface UploadMessageHandler {
        void sendMsg(String taskId, String message);
    }

    public interface UploadCompletionHandler {
        void complete(String fileId);
    }

    public interface UploadFileHandler {
        void uploadLogFile(String zipFilePath, UploadCompletionHandler handler);
    }


    public static class Config {
        int logLevel = INFO;
        boolean fileLogOpen = true;
        boolean consoleLogOpen = false;
        long fileLogMaxAliveTime = 7 * 24 * 60 * 60;
//        long fileLogMaxFileSize = 10 * 1024 * 1024;

        private Config() {

        }

        public Config logLevel(int level) {
            this.logLevel = level;
            return this;
        }

        public Config enableFileLog(boolean open) {
            this.fileLogOpen = open;
            return this;
        }

        public Config enableConsoleLog(boolean open) {
            this.consoleLogOpen = open;
            return this;
        }

        public Config fileLogMaxAliveTime(long seconds) {
            this.fileLogMaxAliveTime = seconds;
            return this;
        }

    }
}
