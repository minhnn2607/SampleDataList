package vn.nms.sample.presentation.ui.features.home

import android.content.Context
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.databinding.FragmentHomeBinding
import vn.nms.sample.presentation.ui.base.fragment.BaseViewModelFragment

@AndroidEntryPoint
class HomeFragment :
    BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun createViewModelClass() = HomeViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_home

    override fun initialize(view: View, ctx: Context?) {

    }
}