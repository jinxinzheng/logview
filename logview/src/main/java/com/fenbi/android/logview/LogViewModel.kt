package com.fenbi.android.logview

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState

/**
 * @author zheng on 12/19/18
 */

data class LogState(
    val logs: List<CharSequence> = emptyList()
) : MvRxState

class LogViewModel(initialState: LogState) : BaseMvRxViewModel<LogState>(initialState) {

    fun addLog(text: CharSequence) = setState {
        copy(logs = (logs + text).takeLast(100))
    }

    fun clear() = setState { copy(logs = emptyList()) }
}

val logViewModel: LogViewModel by lazy { LogViewModel(LogState()) }
