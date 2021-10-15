package vn.nms.sample.presentation.ui.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.qualifiers.ActivityContext

abstract class BasePagedAdapter<T : Any, B : ViewDataBinding>(
    @ActivityContext val context: Context,
    diffUtil: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, BaseViewHolder<B>>(diffUtil) {

    abstract fun itemLayoutResource(): Int

    abstract fun createViewHolder(itemView: View): BaseViewHolder<B>

    protected abstract fun onBindViewHolder(binding: B, dto: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> =
        createViewHolder(
            LayoutInflater.from(context).inflate(itemLayoutResource(), parent, false)
        )

    override fun onBindViewHolder(vh: BaseViewHolder<B>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            this.onBindViewHolder(vh.binding, item, position)
        }
    }

    fun getCurrentItems(): List<T> = snapshot().items
}