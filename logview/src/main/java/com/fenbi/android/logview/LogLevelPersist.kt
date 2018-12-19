package com.fenbi.android.logview

import okhttp3.logging.HttpLoggingInterceptor.Level

/**
 * @author zheng on 12/19/18
 */

internal interface LogLevelPersist {

    var level: Level
}
