package vn.nms.sample.presentation.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import vn.nms.sample.R
import vn.nms.sample.presentation.ui.base.activity.BaseActivity
import vn.nms.sample.presentation.utils.AutoClearedValue

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    lateinit var binding: B

    abstract fun getLayoutResource(): Int

    var firstTimeCreate = true

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (keepInstance()) {
            getPersistentView(inflater, container, savedInstanceState, getLayoutResource())
        } else {
            binding = AutoClearedValue(
                this,
                DataBindingUtil.inflate<B>(inflater, getLayoutResource(), container, false)
            ).get()!!
            binding.root
        }
    }

    open fun isPrimaryColorStatusBar() : Boolean = false

    override fun onResume() {
        super.onResume()
//        if (isPrimaryColorStatusBar()){
//            activity?.window?.apply {
//                statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
//                decorView.let { it.systemUiVisibility = it.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()}
//            }
//        } else {
//            activity?.window?.apply {
//                statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorWhite)
//                decorView.let { it.systemUiVisibility = it.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR }
//            }
//        }
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

    open fun getScreenName(): String = ""

    private var rootView: View? = null

    open fun keepInstance(): Boolean = false

    open fun setLoading(loading: Boolean) {
       (activity as BaseActivity).setLoading(loading)
    }

    fun setDenyAutoHideKeyboard(isDeny: Boolean){
        (activity as BaseActivity).denyHideKeyboard = isDeny
    }

    private fun getPersistentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
        layout: Int
    ): View? {
        if (rootView == null) {
            binding = AutoClearedValue(
                this,
                DataBindingUtil.inflate<B>(inflater, getLayoutResource(), container, false)
            ).get()!!
            rootView = binding.root
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }

        return rootView
    }

    override fun setArguments(args: Bundle?) {
        if (args != null) {
            super.setArguments(Bundle(args).apply {
                putBundle("bundle_args", args)
            })
        } else {
            super.setArguments(null)
        }
    }

}