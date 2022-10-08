package vn.nms.sample.presentation.ui.features.movie.simple.multi_type

import android.content.Context
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.databinding.FragmentMovieListBinding
import vn.nms.sample.presentation.ui.adapter.MovieMultiTypeItemListAdapter
import vn.nms.sample.presentation.ui.base.fragment.BaseViewModelRecyclerViewFragment

@AndroidEntryPoint
class MovieMultiTypeFragment :
    BaseViewModelRecyclerViewFragment<FragmentMovieListBinding, MovieMultiTypeViewModel, MovieMultiTypeItemListAdapter>() {

    override fun createViewModelClass() = MovieMultiTypeViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_movie_list

}