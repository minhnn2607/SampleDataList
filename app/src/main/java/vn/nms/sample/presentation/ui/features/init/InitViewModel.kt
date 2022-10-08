package vn.nms.sample.presentation.ui.features.init

import android.content.Context
import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.nms.sample.data.extensions.onSuccess
import vn.nms.sample.domain.model.intent.InitScreenIntent
import vn.nms.sample.domain.usecase.InitStartUpScreenUseCase
import vn.nms.sample.presentation.ui.base.viewmodel.BaseViewModel
import vn.nms.sample.presentation.utils.NavigatorHelper
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    private val initStartUpScreenUseCase: InitStartUpScreenUseCase,
    private val navigatorHelper: NavigatorHelper
) : BaseViewModel() {

    override fun onFirstTimeUiCreate(bundle: Bundle) {

    }

    fun initialize(context: Context?) {
        initStartUpScreenUseCase().execute(false, onSuccess {
            when (it) {
                is InitScreenIntent.OpenRegister -> {
                    context?.let {
                        navigatorHelper.actionInitToMember(it)
                    }
                }
                is InitScreenIntent.OpenHome -> {
                    context?.let {
                        navigatorHelper.actionOpenMainActivity(context)
                    }

                }
            }
        })
    }
}