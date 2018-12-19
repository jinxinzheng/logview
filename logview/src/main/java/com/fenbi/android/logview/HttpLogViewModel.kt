package com.fenbi.android.logview

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import okhttp3.logging.HttpLoggingInterceptor.Level

/**
 * @author zheng on 12/19/18
 */

data class HttpLogState(
    val visible: Boolean = false,
    val logLevel: Level = Level.BASIC
) : MvRxState

class HttpLogViewModel(initialState: HttpLogState) : BaseMvRxViewModel<HttpLogState>(initialState) {

    init {
        setState { copy(logLevel = HttpLog.logLevel) }
    }

    fun toggleShow() = setState { copy(visible = !visible) }

    fun setLevel(level: Level) {
        setState { copy(logLevel = level) }
        HttpLog.setLevel(level)
    }
}
