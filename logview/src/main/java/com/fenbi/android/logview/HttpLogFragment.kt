package com.fenbi.android.logview

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.forEach
import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import kotlinx.android.synthetic.main.logview_fragment_http_log.*
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.backgroundColor

/**
 * @author zheng on 12/18/18
 */

class BaseEpoxyController(
    private val buildModelsCallback: EpoxyController.() -> Unit
) : AsyncEpoxyController() {

    override fun buildModels() {
        buildModelsCallback()
    }
}

class HttpLogFragment : BaseMvRxFragment() {

    private val viewModel: HttpLogViewModel by fragmentViewModel()
    private val epoxyController by lazy { BaseEpoxyController { buildModels() } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.logview_fragment_http_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        httpText.setOnClickListener {
            viewModel.toggleShow()
        }
        recyclerView.backgroundColor = 0xaa000000.toInt()
        recyclerView.setController(epoxyController)
        (recyclerView.layoutManager as? LinearLayoutManager)?.stackFromEnd = true
        levelSpinner.adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            HttpLoggingInterceptor.Level.values())
        levelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                levelSpinner.selectedItem?.let {
                    viewModel.setLevel(it as HttpLoggingInterceptor.Level)
                }
            }
        }
        clearText.setOnClickListener {
            logViewModel.clear()
        }
        initViewModels()
    }

    private fun initViewModels() {
        logViewModel.subscribe(this) {
            postInvalidate()
        }
        viewModel.selectSubscribe(this, HttpLogState::logLevel) { level ->
            levelSpinner.setSelection(level.ordinal)
        }
        viewModel.selectSubscribe(this, HttpLogState::visible) { visible ->
            (this@HttpLogFragment.view as? ViewGroup)?.forEach {
                if (it.id != R.id.httpText) {
                    it.visible = visible
                }
            }
            httpText.alpha = if (visible) 1f else .5f
        }
    }

    override fun invalidate() {
        recyclerView.requestModelBuild()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
    }

    private fun EpoxyController.buildModels() {
        withState(viewModel, logViewModel) { state, logState ->
            if (!state.visible) {
                return@withState
            }
            logState.logs.forEachIndexed { i, text ->
                logItem {
                    id(i)
                    logText(text)
                }
            }
        }
    }

    companion object {

        inline var View.visible: Boolean
            get() = visibility == View.VISIBLE
            set(value) {
                visibility = if (value) View.VISIBLE else View.GONE
            }
    }
}