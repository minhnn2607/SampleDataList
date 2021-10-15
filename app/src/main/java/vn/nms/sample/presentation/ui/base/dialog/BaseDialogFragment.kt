package vn.nms.sample.presentation.ui.base.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import vn.nms.sample.presentation.utils.AutoClearedValue


abstract class BaseDialogFragment<B : ViewDataBinding> : DialogFragment() {

    lateinit var binding: B

    abstract fun getLayoutResource(): Int

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AutoClearedValue(this, DataBindingUtil.inflate<B>(inflater, getLayoutResource(), container, false)).get()!!
        return binding.root
    }

    protected fun getFragmentArguments(): Bundle? {
        return arguments
    }

    /**
     * @return true if fragment should handle back press, false if not (activity will handle back press event)
     */
    open fun onBackPressed(): Boolean {
        return false
    }
}