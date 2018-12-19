package com.fenbi.android.logview

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.edit
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.matchParent

/**
 * @author zheng on 12/18/18
 */

class LogView(context: Context) : FrameLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.logview_view_log, this, true)
    }

    fun install(activity: FragmentActivity) {
        activity.addContentView(this, ViewGroup.LayoutParams(matchParent, matchParent))
        val fragment = activity.supportFragmentManager.findFragmentByTag("http") as? HttpLogFragment
        if (fragment == null) {
            val httpLogFragment = HttpLogFragment()
            activity.supportFragmentManager.beginTransaction()
                .add(R.id.log_content, httpLogFragment, "http")
                .commit()
        }
    }

    companion object {

        fun install(activity: FragmentActivity) {
            Log.w("LogView", "install")
            HttpLog.logLevelPersist = object : LogLevelPersist {
                override var level: Level
                    get() =
                        activity.defaultSharedPreferences.getString("level", null)
                            ?.let { Level.valueOf(it) } ?: Level.BASIC
                    set(value) {
                        activity.defaultSharedPreferences.edit {
                            putString("level", value.name)
                        }
                    }
            }
            LogView(activity).install(activity)
        }

        fun addLog(text: CharSequence) {
            logViewModel.addLog(text)
        }
    }
}
