package vn.nms.sample.presentation.ui.features.member

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.nms.sample.data.extensions.onSuccess
import vn.nms.sample.data.extensions.showToast
import vn.nms.sample.domain.model.intent.LoginIntent
import vn.nms.sample.domain.usecase.LoginUseCase
import vn.nms.sample.presentation.ui.base.viewmodel.BaseViewModel
import vn.nms.sample.presentation.utils.NavigatorHelper
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val navigatorHelper: NavigatorHelper
) : BaseViewModel() {

    override fun onFirstTimeUiCreate(bundle: Bundle) {

    }

    fun login(context: Context, userName: String, pass: String) {
        loginUseCase(userName, pass).execute(true, onSuccess {
            when (it) {
                is LoginIntent.Success -> {
                    context.showToast(it.message)
                    navigatorHelper.actionOpenMainActivity(context)
                }
                is LoginIntent.Error -> {
                    context.showToast(it.message)
                }
            }
        })
    }
}