package vn.nms.sample.presentation.ui.base.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import vn.nms.sample.domain.define.ErrorState
import vn.nms.sample.domain.define.LoadingState
import vn.nms.sample.domain.define.State
import vn.nms.sample.domain.define.SuccessState
import vn.nms.sample.data.extensions.showToast
import vn.nms.sample.presentation.ui.base.activity.BaseActivity
import vn.nms.sample.presentation.ui.base.viewmodel.BaseViewModel
import vn.nms.sample.presentation.utils.AutoClearedValue
import vn.nms.sample.BR

abstract class BaseBottomSheetViewModelDialogFragment<B : ViewDataBinding, VM : BaseViewModel> :
    BaseBottomSheetDialogFragment<B>() {

    lateinit var viewModel: VM

    protected abstract fun createViewModelClass(): Class<VM>

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AutoClearedValue(
            this,
            DataBindingUtil.inflate<B>(inflater, getLayoutResource(), container, false)
        ).get()!!
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity !is BaseActivity) {
            throw IllegalStateException("All fragment's container must extend BaseActivity")
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(createViewModelClass())
        binding.setVariable(BR.vm, viewModel)
        viewModel.stateLiveData.observe(viewLifecycleOwner, { handleState(it) })
        initialize(view, activity)
    }

    protected abstract fun initialize(view: View, ctx: Context?)

    open fun handleState(state: State?) {
        setLoading(state != null && state is LoadingState)
        handleMessageState(state)
    }

    private fun handleMessageState(@Nullable state: State?) {
        if ((state is SuccessState || state is ErrorState) && state.message.isNotEmpty()) {
            showMessage(state.message)
        }
    }

    private fun showMessage(message: String?) {
        lifecycleScope.launch {
            context?.showToast(message)
        }
    }

    open fun setLoading(loading: Boolean) {
        (activity as BaseActivity).setLoading(loading)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroyView()
    }
}