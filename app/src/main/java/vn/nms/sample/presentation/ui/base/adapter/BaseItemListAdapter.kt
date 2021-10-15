package vn.nms.sample.presentation.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dagger.hilt.android.qualifiers.ActivityContext
import vn.nms.sample.domain.itemviewmodel.ItemViewModel
import vn.nms.sample.data.extensions.genericCastOrNull

/**
 * Configuration object for {@link ListAdapter}, {@link AsyncListDiffer}, and similar
 * background-thread list diffing adapter logic.
 * <p>
 * At minimum, defines item diffing behavior with a {@link DiffUtil.ItemCallback}, used to compute
 * item differences to pass to a RecyclerView adapter.
 * @sample submitList Used for observer change ItemViewModel (Api load more)
 */

abstract class BaseItemListAdapter(
    @ActivityContext val context: Context,
    diffUtil: DiffUtil.ItemCallback<ItemViewModel>
) : ListAdapter<ItemViewModel, BaseItemViewHolder<ItemViewModel, ViewDataBinding>>(diffUtil) {

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
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseItemViewHolder<ItemViewModel, ViewDataBinding> = createItemViewHolder(
        LayoutInflater.from(context).inflate(getLayoutResource(viewType), parent, false), viewType
    )

    fun getItemAt(position: Int): ItemViewModel? = getItem(position)

    inline fun <reified T> getItemAt(position: Int): T? = genericCastOrNull(getItemAt(position))

    abstract fun setItemViewType(item: ItemViewModel?): Int

    abstract fun getLayoutResource(viewType: Int): Int

    abstract fun createItemViewHolder(
        view: View,
        viewType: Int
    ): BaseItemViewHolder<ItemViewModel, ViewDataBinding>
}

