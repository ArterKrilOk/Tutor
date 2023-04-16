package space.pixelsg.tutuor.common

import android.R
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Spinner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object UiUtils {
    fun Snackbar.top(): Snackbar = apply {
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
    }

    fun LifecycleOwner.repeatOnCreated(block: suspend CoroutineScope.() -> Unit) {
//        lifecycleScope.launchWhenCreated(block)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED, block)
        }
    }

    fun <T> Flow<T>.collectOnOnLifecycle(
        owner: LifecycleOwner,
        state: Lifecycle.State = Lifecycle.State.CREATED,
        collector: FlowCollector<T>
    ) {
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(state) {
                collect(collector)
            }
        }
    }

    fun <T> Flow<T>.collectIn(coroutineScope: CoroutineScope, collector: FlowCollector<T>) {
        coroutineScope.launch {
            collect(collector)
        }
    }

    fun EditText.replaceText(newText: String) {
        if (text.toString() != newText) setText(newText)
    }

    fun EditText.onTextChanged(debounce: Long = 200) = callbackFlow {
        val listener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                runBlocking { send(p0.toString()) }
            }

            override fun afterTextChanged(p0: Editable?) {
                runBlocking { send(p0.toString()) }
            }
        }
        addTextChangedListener(listener)
        send(text.toString())
        awaitClose { removeTextChangedListener(listener) }
    }.debounce(debounce).distinctUntilChanged()

    fun <T> Spinner.setupSimple(
        data: List<T>,
        converter: (T) -> String = { it.toString() }
    ): Flow<T> = callbackFlow {
        val arrayAdapter = ArrayAdapter(context, R.layout.simple_spinner_item, data.map(converter))
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        adapter = arrayAdapter

        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                runBlocking { send(data[position]) }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                setSelection(0)
            }

        }
        awaitClose { onItemSelectedListener = null }
    }
}