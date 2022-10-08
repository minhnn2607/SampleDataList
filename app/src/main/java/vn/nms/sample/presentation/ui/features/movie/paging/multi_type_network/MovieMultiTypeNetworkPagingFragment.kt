package vn.nms.sample.presentation.ui.features.movie.paging.multi_type_network

import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.databinding.FragmentMovieListBinding
import vn.nms.sample.presentation.ui.adapter.MovieMultiTypePagingAdapter
import vn.nms.sample.presentation.ui.base.fragment.BaseViewModelRecyclerViewPagingFragment

@AndroidEntryPoint
class MovieMultiTypeNetworkPagingFragment :
    BaseViewModelRecyclerViewPagingFragment<FragmentMovieListBinding, MovieMultiTypeNetworkPagingViewModel, MovieMultiTypePagingAdapter>() {

    override fun createViewModelClass() = MovieMultiTypeNetworkPagingViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_movie_list
}