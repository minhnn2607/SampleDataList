package vn.nms.sample.presentation.ui.base.activity

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import vn.nms.sample.data.extensions.showToast
import vn.nms.sample.domain.define.ErrorState
import vn.nms.sample.domain.define.LoadingState
import vn.nms.sample.domain.define.State
import vn.nms.sample.domain.define.SuccessState
import vn.nms.sample.presentation.ui.base.viewmodel.BaseViewModel

abstract class BaseViewModelActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    @VisibleForTesting
    lateinit var binding: B

    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResource())
        viewModel = ViewModelProvider(this).get(createViewModelClass())
        viewModel.onCreate(intent.extras)
        viewModel.stateLiveData.observe(this, { handleState(it) })
        initObserver()
    }

    protected abstract fun initObserver()

    protected abstract fun createViewModelClass(): Class<VM>

    /**
     * Default state handling, can be override
     * @param state viewModel's state
     */
    open fun handleState(state: State?) {
        handleMessageState(state)
        setLoading(state != null && state is LoadingState)
    }

    private fun handleMessageState(@Nullable state: State?) {
        if ((state is SuccessState || state is ErrorState) && state.message.isNotEmpty()) {
            showToast(state.message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun shouldUseDataBinding() = true

//    override fun onBackPressed() {
//        val countFragment = supportFragmentManager.backStackEntryCount
//        if (countFragment == 1) {
//            finish()
//        } else {
//            super.onBackPressed()
//        }
//    }

    override fun getScreenName(): String? = null

}