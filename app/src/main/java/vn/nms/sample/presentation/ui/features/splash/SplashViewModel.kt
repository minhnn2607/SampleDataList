package vn.nms.sample.presentation.ui.features.splash

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.nms.sample.domain.usecase.InitUseCase
import vn.nms.sample.data.extensions.onSuccess
import vn.nms.sample.domain.model.intent.SplashIntent
import vn.nms.sample.presentation.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val initUseCase: InitUseCase
) : BaseViewModel() {

    var intent = MutableLiveData<SplashIntent>()

    fun initialize() {
        viewModelScope.launch {
            delay(600L)
            initUseCase().execute(false, onSuccess {
                intent.postValue(it)
            })
        }
    }

    override fun onFirstTimeUiCreate(bundle: Bundle) {
        initialize()
    }
}