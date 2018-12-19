package com.fenbi.android.logview

import android.content.Context
import android.widget.TextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import org.jetbrains.anko.textColor

/**
 * @author zheng on 12/18/18
 */

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class LogItem(context: Context) : TextView(context) {

    init {
        textSize = 10f
    }

    @ModelProp
    fun setLogText(text: CharSequence?) {
        this.text = text
        textColor = if (text != null && (text.startsWith("<--") || text.startsWith("-->"))) {
            if (text.startsWith("<--") && !text.startsWith("<-- 200") && !text.startsWith("<-- END")) {
                0xffee5555.toInt()
            } else {
                0xffcceeff.toInt()
            }
        } else {
            0xffffffff.toInt()
        }
    }
}
