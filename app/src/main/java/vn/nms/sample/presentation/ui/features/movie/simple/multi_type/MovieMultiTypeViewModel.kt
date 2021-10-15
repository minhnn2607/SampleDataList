package vn.nms.sample.presentation.ui.features.movie.simple.multi_type

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.nms.sample.data.extensions.onSuccess
import vn.nms.sample.domain.usecase.GetMovieMultiTypeUseCase
import vn.nms.sample.presentation.ui.adapter.MovieMultiTypeItemListAdapter
import vn.nms.sample.presentation.ui.base.viewmodel.BaseListViewModel
import javax.inject.Inject

@HiltViewModel
class MovieMultiTypeViewModel @Inject constructor(
    private val getMovieMultiTypeUseCase: GetMovieMultiTypeUseCase
) : BaseListViewModel<MovieMultiTypeItemListAdapter>() {

    override fun fetchData() {
    }

    override fun onFirstTimeUiCreate(bundle: Bundle) {
        getMovieMultiTypeUseCase().execute(true, onSuccess {
            adapter.submitList(it)
        })
    }
}