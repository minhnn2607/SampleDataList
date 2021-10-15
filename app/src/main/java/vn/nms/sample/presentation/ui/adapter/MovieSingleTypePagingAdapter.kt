package vn.nms.sample.presentation.ui.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import vn.nms.sample.R
import vn.nms.sample.databinding.ItemMovieBinding
import vn.nms.sample.domain.itemviewmodel.MovieItemViewModel
import vn.nms.sample.domain.model.MovieModel
import vn.nms.sample.presentation.ui.base.adapter.BaseItemViewHolder
import vn.nms.sample.presentation.ui.base.adapter.BasePagedAdapter
import vn.nms.sample.presentation.ui.base.adapter.BaseViewHolder
import vn.nms.sample.presentation.utils.image.ImageLoader
import javax.inject.Inject

class MovieSingleTypePagingAdapter @Inject constructor(
    @ApplicationContext context: Context,
    private val imageLoader: ImageLoader
) :
    BasePagedAdapter<MovieModel, ItemMovieBinding>(context, object : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun itemLayoutResource() = R.layout.item_movie

    override fun createViewHolder(itemView: View): BaseViewHolder<ItemMovieBinding> {
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(binding: ItemMovieBinding, dto: MovieModel, position: Int) {
        binding.imageLoader = imageLoader
        binding.dto = dto
    }

    inner class MovieViewHolder(view: View) :
        BaseItemViewHolder<MovieItemViewModel, ItemMovieBinding>(view) {
        init {

        }

        override fun setItem(data: MovieItemViewModel, binding: ItemMovieBinding) {
            super.setItem(data, binding)
            binding.imageLoader = imageLoader
            binding.dto = data.movie
        }
    }
}