package vn.nms.sample.presentation.ui.features.movie.paging.multi_type_network_db

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.nms.sample.data.extensions.onSuccess
import vn.nms.sample.domain.usecase.GetMovieMultiTypeNetworkDbPagingUseCase
import vn.nms.sample.presentation.ui.adapter.MovieMultiTypePagingAdapter
import vn.nms.sample.presentation.ui.base.viewmodel.BaseListViewModel
import javax.inject.Inject

@HiltViewModel
class MovieMultiTypeNetworkDbPagingViewModel @Inject constructor(
    private val getMovieMultiTypeNetworkDbPagingUseCase: GetMovieMultiTypeNetworkDbPagingUseCase
) : BaseListViewModel<MovieMultiTypePagingAdapter>() {

    override fun fetchData() {
    }

    override fun onFirstTimeUiCreate(bundle: Bundle) {
        getMovieMultiTypeNetworkDbPagingUseCase().execute(false, onSuccess {
            viewModelScope.launch {
                adapter.submitData(it)
            }
        })
    }

    override fun refresh() {
        adapter.refresh()
    }
}