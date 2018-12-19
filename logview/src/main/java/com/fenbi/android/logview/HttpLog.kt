package com.fenbi.android.logview

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level

/**
 * @author zheng on 12/19/18
 */

internal object HttpLog {

    val httpLoggingInterceptor: HttpLoggingInterceptor by lazy {
        Log.w("LogView", "httpLoggingInterceptor")
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            logViewModel.addLog(message)
        }
        loggingInterceptor.level = logLevelPersist?.level ?: Level.BASIC
        loggingInterceptor
    }

    var logLevel: Level
        get() = httpLoggingInterceptor.level
        private set(value) { httpLoggingInterceptor.level = value }

    var logLevelPersist: LogLevelPersist? = null
        set(value) {
            field = value
            if (value != null && logLevel != value.level) {
                logLevel = value.level
            }
        }

    fun setLevel(level: Level) {
        logLevel = level
        logLevelPersist?.level = level
    }
}
