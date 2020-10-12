package com.aibb.android.base.log.xlog;

import android.content.Context;

import com.aibb.android.base.log.LogCollect;
import com.aibb.android.base.log.utils.Utils;
import com.tencent.mars.xlog.Xlog;

import java.io.File;
import timber.log.Timber;

/**
 * Author: aibingbing <br>
 * Date: 2019-09-12 <br>
 * Desc: <br>
 * Edit History: XLog 微信日志组件，用于日志持久化。目前保留 5 天内的日志文件，过期自动删除
 *
 * @see <a href="https://github.com/Tencent/mars">Mars 微信跨平台终端基础组件</a>
 * @see <a href="https://mp.weixin.qq.com/s/cnhuEodJGIbdodh0IxNeXQ">xlog 微信日志模块</a>
 * <br>
 */
public final class XLogHelper {
    private static final String publicKey = "43b6ed591ff2981807d75b420160d8fbfe5316ac22d93e63082c16761ce50d2dc769b0fe421bff4bc5bc8087a70a804d99af3adec32967d18d27bd54d7fa090c";
    private static boolean initialized = false;

    private XLogHelper() {
    }

    public static void init(
            Context context,
            File logDir,
            File cacheDir,
            int logLevel,
            long fileLogMaxAliveTime
    ) {
        if (initialized) {
            Timber.w("XLog already initialized, do not repeat init");
            return;
        }

        String processName = Utils.getProcessName(context);
        if (processName.isEmpty()) {
            Timber.w("无法获取进程名，为防止日志文件冲突，中止 XLog 初始化");
            return;
        }

        String logFileNameProfix;
        if (!processName.contains(":")) {
            logFileNameProfix = LogCollect.TAG;// main process
        } else {
            logFileNameProfix = LogCollect.TAG + "_" + processName.substring(processName.indexOf(":") + 1); // other process
        }
        try {
            Xlog.open(
                    true,
                    logLevel,
                    Xlog.AppednerModeAsync,
                    cacheDir.getAbsolutePath(),
                    logDir.getAbsolutePath(),
                    logFileNameProfix,
                    publicKey
            );
            // should be called before appenderOpen to take effect, duration alive seconds
            Xlog.setMaxAliveTime(fileLogMaxAliveTime);
            Xlog.setConsoleLogOpen(false); // 控制台日志由 Timber 管理，XLog 统一关闭
            com.tencent.mars.xlog.Log.setLogImp(new Xlog());
            initialized = true;
        } catch (Exception e) {
            Timber.e(e, "XLog init error");
            initialized = false;
        }
    }

    public static void log(int priority, String tag, String message) {
        if (!initialized) {
            return;
        }
        switch (priority) {
            case LogCollect.VERBOSE:
                com.tencent.mars.xlog.Log.v(tag, message);
                break;
            case LogCollect.DEBUG:
                com.tencent.mars.xlog.Log.d(tag, message);
                break;
            case LogCollect.INFO:
                com.tencent.mars.xlog.Log.i(tag, message);
                break;
            case LogCollect.WARN:
                com.tencent.mars.xlog.Log.w(tag, message);
                break;
            case LogCollect.ERROR:
                com.tencent.mars.xlog.Log.e(tag, message);
                break;
            default:
                break;
        }
    }

    public static void close() {
        com.tencent.mars.xlog.Log.appenderClose();
    }

    public static void flush(boolean isSync) {
        com.tencent.mars.xlog.Log.appenderFlush(isSync);
    }
}
