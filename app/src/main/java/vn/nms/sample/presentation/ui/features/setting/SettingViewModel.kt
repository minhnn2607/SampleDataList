package vn.nms.sample.presentation.ui.features.setting

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.nms.sample.data.extensions.onSuccess
import vn.nms.sample.domain.usecase.LogOutUseCase
import vn.nms.sample.presentation.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase
) : BaseViewModel() {


    override fun onFirstTimeUiCreate(bundle: Bundle) {
    }

    fun logOut(context: Context) {
        logOutUseCase().execute(true, onSuccess {
            navigationHelper.actionOpenInitActivity(context)
        })
    }
}