package vn.nms.sample.presentation.ui.features.member

import android.content.Context
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.data.extensions.showToast
import vn.nms.sample.databinding.FragmentMemberBinding
import vn.nms.sample.domain.model.intent.LoginIntent
import vn.nms.sample.presentation.ui.base.fragment.BaseViewModelFragment

@AndroidEntryPoint
class MemberFragment : BaseViewModelFragment<FragmentMemberBinding, MemberViewModel>() {

    override fun createViewModelClass() = MemberViewModel::class.java

    override fun initialize(view: View, ctx: Context?) {
        binding.btnLogin.setOnClickListener {
            viewModel.login(it.context, binding.etUserName.text.toString(), binding.etPass.text.toString())
        }
    }
    override fun getLayoutResource() = R.layout.fragment_member

}