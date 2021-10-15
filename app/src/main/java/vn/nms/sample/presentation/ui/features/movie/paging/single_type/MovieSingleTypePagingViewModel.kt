package vn.nms.sample.presentation.ui.features.movie.paging.single_type

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.nms.sample.data.extensions.onSuccess
import vn.nms.sample.domain.usecase.GetMovieSingleTypePagingUseCase
import vn.nms.sample.presentation.ui.adapter.MovieSingleTypePagingAdapter
import vn.nms.sample.presentation.ui.base.viewmodel.BaseListViewModel
import javax.inject.Inject

@HiltViewModel
class MovieSingleTypePagingViewModel @Inject constructor(
    private val getMovieSingleTypePagingUseCase: GetMovieSingleTypePagingUseCase
) : BaseListViewModel<MovieSingleTypePagingAdapter>() {

    override fun fetchData() {
    }

    override fun onFirstTimeUiCreate(bundle: Bundle) {
        getMovieSingleTypePagingUseCase().execute(false, onSuccess {
            viewModelScope.launch {
                adapter.submitData(it)
            }
        })
    }

    override fun refresh() {
        adapter.refresh()
    }
}