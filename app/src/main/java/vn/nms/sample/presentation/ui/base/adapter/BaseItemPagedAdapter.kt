package vn.nms.sample.presentation.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.qualifiers.ActivityContext
import vn.nms.sample.domain.itemviewmodel.ItemViewModel

abstract class BaseItemPagedAdapter(
    @ActivityContext val context: Context,
    diffUtil: DiffUtil.ItemCallback<ItemViewModel>
) : PagingDataAdapter<ItemViewModel, BaseItemViewHolder<ItemViewModel, ViewDataBinding>>(diffUtil) {

    override fun getItemViewType(position: Int): Int {
        return setItemViewType(getItemAt(position))
    }

    override fun onBindViewHolder(
        holder: BaseItemViewHolder<ItemViewModel, ViewDataBinding>,
        position: Int
    ) {
        val item = getItemAt(position)
        if (item != null) {
            holder.setItem(item, holder.binding)
            holder.binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseItemViewHolder<ItemViewModel, ViewDataBinding> {
        val viewHolder = createItemViewHolder(
            LayoutInflater.from(context).inflate(getLayoutResource(viewType), parent, false),
            viewType
        )
        return viewHolder
    }

    fun getItemAt(position: Int): ItemViewModel? = getItem(position)

    fun getCurrentItems(): List<ItemViewModel> = snapshot().items

    abstract fun setItemViewType(item: ItemViewModel?): Int

    abstract fun getLayoutResource(viewType: Int): Int

    abstract fun createItemViewHolder(
        view: View,
        viewType: Int
    ): BaseItemViewHolder<ItemViewModel, ViewDataBinding>

}
