package vn.nms.sample.presentation.ui.base.activity

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vn.nms.sample.data.extensions.createLoadingDialog

abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: Dialog? = null

    var denyHideKeyboard: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!shouldUseDataBinding()) {
            // set contentView if child activity not use dataBinding
            setContentView(getLayoutResource())
        }
    }

    open fun setLoading(loading: Boolean) {
        if (loading) {
            hideLoading()
            if (progressDialog == null) {
                progressDialog = createLoadingDialog()
            } else {
                progressDialog = null
                progressDialog = createLoadingDialog()
            }
            progressDialog?.show()
        } else {
            hideLoading()
        }
    }

    fun hideLoading() {
        if (progressDialog != null && progressDialog?.isShowing == true)
            progressDialog?.dismiss()
    }

    /**
     * @return true if child activity should use data binding instead of [.setContentView]
     */
    protected open fun shouldUseDataBinding(): Boolean {
        return false
    }

    protected abstract fun getLayoutResource(): Int

    protected abstract fun getScreenName(): String?

    override fun onDestroy() {
        super.onDestroy()
        hideLoading()
        progressDialog = null
    }

}