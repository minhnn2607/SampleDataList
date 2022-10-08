package vn.nms.sample.presentation.ui.features.movie.simple.single_type

import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.databinding.FragmentMovieListBinding
import vn.nms.sample.presentation.ui.adapter.MovieSingleTypeListAdapter
import vn.nms.sample.presentation.ui.base.fragment.BaseViewModelRecyclerViewFragment

@AndroidEntryPoint
class MovieSingleTypeFragment :
    BaseViewModelRecyclerViewFragment<FragmentMovieListBinding, MovieSingleTypeViewModel, MovieSingleTypeListAdapter>() {

    override fun createViewModelClass() = MovieSingleTypeViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_movie_list
}