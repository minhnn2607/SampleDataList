package vn.nms.sample.presentation.ui.features.movie.simple.single_type

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.nms.sample.data.extensions.onSuccess
import vn.nms.sample.domain.usecase.GetMovieSingleTypeUseCase
import vn.nms.sample.presentation.ui.adapter.MovieSingleTypeListAdapter
import vn.nms.sample.presentation.ui.base.viewmodel.BaseListViewModel
import javax.inject.Inject

@HiltViewModel
class MovieSingleTypeViewModel @Inject constructor(
    private val getMovieSingleTypeUseCase: GetMovieSingleTypeUseCase
) : BaseListViewModel<MovieSingleTypeListAdapter>() {

    override fun fetchData() {
    }

    override fun onFirstTimeUiCreate(bundle: Bundle) {
        getMovieSingleTypeUseCase().execute(true, onSuccess {
            adapter.submitList(it)
        })
    }
}