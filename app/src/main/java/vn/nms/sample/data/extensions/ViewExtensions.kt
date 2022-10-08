package vn.nms.sample.data.extensions

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import vn.nms.sample.R
import java.util.concurrent.TimeUnit

fun Context.showDialog(
    title: String? = null,
    message: String,
    isCancelable: Boolean = true,
    positiveText: String? = getString(R.string.ok),
    onClickPositive: (() -> Unit)? = null,
    negativeText: String? = getString(R.string.cancel),
    onClickNegative: (() -> Unit)? = null
) {
    val builder = AlertDialog.Builder(this).apply {
        if (!title.isNullOrEmpty()) {
            setTitle(title)
        }
        setMessage(message)

        if (positiveText != null) {
            setPositiveButton(positiveText) { _, _ -> onClickPositive?.invoke() }
        }
        if (negativeText != null) {
            setNegativeButton(negativeText) { _, _ -> onClickNegative?.invoke() }
        }
    }
    builder.setCancelable(isCancelable)
    val dialog: AlertDialog = builder.create()
    dialog.show()
//    val buttonNegative: Button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
//    buttonNegative.setTextColor(ContextCompat.getColor(this, R.color.colorGrayHard))
}

fun Context.showToast(message: String?) {
    if (!message.isNullOrEmpty()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context.showToast(messageIds: Int?) {
    if (messageIds != null) {
        Toast.makeText(this, getString(messageIds), Toast.LENGTH_SHORT).show()
    }
}

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun EditText.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.createLoadingDialog(): Dialog {
    val progressDialog = Dialog(this)
    progressDialog.let {
        it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        it.setContentView(R.layout.dialog_progress)
        it.setCancelable(false)
        it.setCanceledOnTouchOutside(false)
        return it
    }
}

fun EditText.textChanges(timeout: Long = 500, onTextChangeListener: (String) -> Unit) {
    this.textChangeEvents().debounce(timeout, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        .subscribe { charSequence -> onTextChangeListener.invoke(charSequence.text.toString()) }
}

fun View.afterLayout(what: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            what.invoke()
        }
    })
}

fun EditText.focus() {
    requestFocus()
    setSelection(length())
}

fun RecyclerView.addScrollListener(
    onLoadMore: () -> Unit,
    onIdle: ((isIdle: Boolean) -> Unit)? = null
) {
    val layoutManager = layoutManager as LinearLayoutManager
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            if (totalItemCount <= (lastVisibleItem + 5)) {
                onLoadMore.invoke()
            }
        }

        override fun onScrollStateChanged(
            recyclerView: RecyclerView,
            newState: Int
        ) {
            if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                onIdle?.invoke(false)
            } else {
                onIdle?.invoke(true)
            }
        }
    })
}

fun View.getLocationOnScreen(): PointF {
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    return PointF(location[0].toFloat(), location[1].toFloat())
}

fun View.getParentWithTag(tag: String): View? {
    val parent = this.parent
    return if (parent is View) {
        if (parent.tag == tag) parent
        else parent.getParentWithTag(tag)
    } else {
        null
    }
}

