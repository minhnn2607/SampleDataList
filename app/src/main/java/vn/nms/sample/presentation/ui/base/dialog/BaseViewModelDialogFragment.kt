package vn.nms.sample.presentation.ui.base.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.Nullable
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
import vn.nms.sample.BR

abstract class BaseViewModelDialogFragment<B : ViewDataBinding, VM : BaseViewModel> : BaseDialogFragment<B>() {

    lateinit var viewModel: VM

    protected abstract fun createViewModelClass(): Class<VM>

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(createViewModelClass())
        binding.setVariable(BR.vm,viewModel)
        viewModel.stateLiveData.observe(viewLifecycleOwner, { handleState(it) })
//        view.setBackgroundResource(R.drawable.dialog_rounded_bg)
        initialize(view, activity)
    }

    override fun onStart() {
        super.onStart()
        this.dialog?.window?.let {
//            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//            it.setGravity(Gravity.BOTTOM)
//            it.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            it.setWindowAnimations(R.style.DialogAnimation)
        }
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