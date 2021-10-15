package vn.nms.sample.presentation.ui.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.nms.sample.domain.define.ErrorState
import vn.nms.sample.domain.define.LoadingState
import vn.nms.sample.domain.define.State
import vn.nms.sample.domain.define.SuccessState
import vn.nms.sample.data.extensions.showToast
import vn.nms.sample.presentation.ui.base.viewmodel.BaseViewModel
import vn.nms.sample.presentation.utils.NavigatorHelper
import vn.nms.sample.presentation.utils.image.ImageLoader
import javax.inject.Inject
import vn.nms.sample.BR

abstract class BaseViewModelFragment<B : ViewDataBinding, VM : BaseViewModel> : BaseFragment<B>() {

    lateinit var viewModel: VM

    @Inject
    lateinit var navigatorHelper: NavigatorHelper

    @Inject
    lateinit var imageLoader: ImageLoader

    private var isShowingToast: Boolean = false

    protected abstract fun createViewModelClass(): Class<VM>

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(createViewModelClass())
        viewModel.onCreate(getFragmentArguments())
        binding.setVariable(BR.vm, viewModel)
        viewModel.stateLiveData.observe(viewLifecycleOwner, {
            handleState(it)
        })
        initialize(view, requireActivity())
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed(), {
            viewModel.onBackPressed {
                parentFragment?.findNavController()?.navigateUp()
            }
        })
    }

    protected abstract fun initialize(view: View, ctx: Context?)

    open fun handleState(state: State?) {
        setLoading(
            loading = state != null && state is LoadingState
        )
        handleMessageState(state)
    }

    private fun handleMessageState(@Nullable state: State?) {
        if ((state is SuccessState || state is ErrorState) && state.message.isNotEmpty()) {
            showMessage(state.message)
        }
    }

    private fun showMessage(message: String?) {
        if (!isShowingToast) {
            lifecycleScope.launch {
                isShowingToast = true
                context?.showToast(message)
                delay(2000L)
                isShowingToast = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroyView()
        setLoading(false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}