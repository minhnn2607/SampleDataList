package vn.nms.sample.presentation.ui.features.setting

import android.content.Context
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.databinding.FragmentSettingBinding
import vn.nms.sample.presentation.ui.base.fragment.BaseViewModelFragment

@AndroidEntryPoint
class SettingFragment : BaseViewModelFragment<FragmentSettingBinding, SettingViewModel>() {

    override fun createViewModelClass() = SettingViewModel::class.java

    override fun initialize(view: View, ctx: Context?) {

    }
    override fun getLayoutResource() = R.layout.fragment_setting

}