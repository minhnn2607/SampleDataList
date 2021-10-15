package vn.nms.sample.presentation.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dagger.hilt.android.qualifiers.ActivityContext

/**
 * Configuration object for {@link ListAdapter}, {@link AsyncListDiffer}, and similar
 * background-thread list diffing adapter logic.
 * <p>
 * At minimum, defines item diffing behavior with a {@link DiffUtil.ItemCallback}, used to compute
 * item differences to pass to a RecyclerView adapter.
 * @param <T> Type of the lists this AsyncListDiffer will receive.
 * @sample submitList Used for observer change List<T> (Api load more)
 */

abstract class BaseListAdapter<T, B : ViewDataBinding>(
    @ActivityContext val context: Context,
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<B>>(diffUtil) {

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        val item = getItemAt(position)
        if (item != null) {
            this.onBindViewHolder(holder.binding, item, position)
            holder.binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> =
        createViewHolder(LayoutInflater.from(context).inflate(itemLayoutResource(), parent, false))

    abstract fun itemLayoutResource(): Int

    abstract fun createViewHolder(itemView: View): BaseViewHolder<B>

    abstract fun onBindViewHolder(binding: B, dto: T, position: Int)

    fun getData(): List<T> = this.currentList

    fun getItemAt(position: Int): T? = getItem(position)
}
