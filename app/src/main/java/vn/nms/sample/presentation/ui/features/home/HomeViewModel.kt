package vn.nms.sample.presentation.ui.features.home

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.nms.sample.presentation.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel() {

    override fun onFirstTimeUiCreate(bundle: Bundle) {

    }
}