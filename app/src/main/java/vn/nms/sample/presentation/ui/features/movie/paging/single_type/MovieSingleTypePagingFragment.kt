package vn.nms.sample.presentation.ui.features.movie.paging.single_type

import android.content.Context
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.databinding.FragmentMovieListBinding
import vn.nms.sample.presentation.ui.adapter.MovieSingleTypePagingAdapter
import vn.nms.sample.presentation.ui.base.fragment.BaseViewModelRecyclerViewPagingFragment

@AndroidEntryPoint
class MovieSingleTypePagingFragment :
    BaseViewModelRecyclerViewPagingFragment<FragmentMovieListBinding, MovieSingleTypePagingViewModel, MovieSingleTypePagingAdapter>() {

    override fun createViewModelClass() = MovieSingleTypePagingViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_movie_list

    override fun initialize(view: View, ctx: Context?) {
        super.initialize(view, ctx)
    }
}