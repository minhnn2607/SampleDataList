package vn.nms.sample.presentation.ui.features.init

import android.content.Context
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.databinding.FragmentInitBinding
import vn.nms.sample.presentation.ui.base.fragment.BaseViewModelFragment

@AndroidEntryPoint
class InitFragment : BaseViewModelFragment<FragmentInitBinding, InitViewModel>() {

    override fun createViewModelClass() = InitViewModel::class.java

    override fun initialize(view: View, ctx: Context?) {
        viewModel.initialize(ctx)
    }

    override fun getLayoutResource() = R.layout.fragment_init
}