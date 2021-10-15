package vn.nms.sample.presentation.ui.features.movie.paging.multi_type_network_db

import android.content.Context
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.databinding.FragmentMovieListBinding
import vn.nms.sample.presentation.ui.adapter.MovieMultiTypePagingAdapter
import vn.nms.sample.presentation.ui.base.fragment.BaseViewModelRecyclerViewPagingFragment

@AndroidEntryPoint
class MovieMultiTypeNetworkDatabasePagingFragment :
    BaseViewModelRecyclerViewPagingFragment<FragmentMovieListBinding, MovieMultiTypeNetworkDbPagingViewModel, MovieMultiTypePagingAdapter>() {

    override fun createViewModelClass() = MovieMultiTypeNetworkDbPagingViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_movie_list

    override fun initialize(view: View, ctx: Context?) {
        super.initialize(view, ctx)
    }
}