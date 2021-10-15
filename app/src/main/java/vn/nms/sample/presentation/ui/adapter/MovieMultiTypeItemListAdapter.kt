package vn.nms.sample.presentation.ui.adapter

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import vn.nms.sample.R
import vn.nms.sample.data.extensions.genericCast
import vn.nms.sample.databinding.ItemAdBinding
import vn.nms.sample.databinding.ItemMovieBinding
import vn.nms.sample.domain.itemviewmodel.AdItemViewModel
import vn.nms.sample.domain.itemviewmodel.ItemViewModel
import vn.nms.sample.domain.itemviewmodel.MovieItemViewModel
import vn.nms.sample.presentation.ui.base.adapter.BaseItemListAdapter
import vn.nms.sample.presentation.ui.base.adapter.BaseItemViewHolder
import vn.nms.sample.presentation.ui.viewholder.EmptyViewHolder
import vn.nms.sample.presentation.utils.image.ImageLoader
import javax.inject.Inject

class MovieMultiTypeItemListAdapter @Inject constructor(
    @ApplicationContext context: Context,
    private val imageLoader: ImageLoader
) :
    BaseItemListAdapter(context, object : DiffUtil.ItemCallback<ItemViewModel>() {
        override fun areItemsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return when {
                oldItem is MovieItemViewModel && newItem is MovieItemViewModel -> oldItem.idViewModel == newItem.idViewModel
                oldItem is AdItemViewModel && newItem is AdItemViewModel -> oldItem.idViewModel == newItem.idViewModel
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ItemViewModel, newItem: ItemViewModel): Boolean {
            return when {
                oldItem is MovieItemViewModel && newItem is MovieItemViewModel -> oldItem.movie == newItem.movie
                oldItem is AdItemViewModel && newItem is AdItemViewModel -> oldItem.ad == newItem.ad
                else -> false
            }
        }
    }) {

    override fun setItemViewType(item: ItemViewModel?): Int {
        return when (item) {
            is MovieItemViewModel -> ITEM_MOVIE
            is AdItemViewModel -> ITEM_AD
            else -> ITEM_EMPTY
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            ITEM_MOVIE -> R.layout.item_movie
            ITEM_AD -> R.layout.item_ad
            else -> R.layout.item_empty
        }
    }

    override fun createItemViewHolder(
        view: View,
        viewType: Int
    ): BaseItemViewHolder<ItemViewModel, ViewDataBinding> {
        return when (viewType) {
            ITEM_MOVIE -> genericCast(MovieViewHolder(view))
            ITEM_AD -> genericCast(AdViewHolder(view))
            else -> genericCast(EmptyViewHolder(view))
        }
    }

    companion object {
        const val ITEM_MOVIE = 1
        const val ITEM_AD = 2
        const val ITEM_EMPTY = 3
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

    inner class AdViewHolder(view: View) :
        BaseItemViewHolder<AdItemViewModel, ItemAdBinding>(view) {
        init {

        }

        override fun setItem(data: AdItemViewModel, binding: ItemAdBinding) {
            super.setItem(data, binding)
            binding.imageLoader = imageLoader
            binding.dto = data.ad
        }
    }
}