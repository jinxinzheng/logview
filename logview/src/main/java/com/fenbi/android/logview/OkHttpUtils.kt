package com.fenbi.android.logview

import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.internal.Util

/**
 * @author zheng on 12/19/18
 */

fun Builder.addLogViewInterceptor(): Builder {
    addNetworkInterceptor(HttpLog.httpLoggingInterceptor)
    return this
}

fun OkHttpClient.addLogViewInterceptor() {
    val networkInterceptors = networkInterceptors()
    val newList = Util.immutableList(networkInterceptors + HttpLog.httpLoggingInterceptor)
    try {
        val networkInterceptorsField = this@addLogViewInterceptor.javaClass.getDeclaredField("networkInterceptors")
        networkInterceptorsField.isAccessible = true
        networkInterceptorsField.set(this, newList)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}
