package vn.nms.sample.presentation.ui.base.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import vn.nms.sample.domain.itemviewmodel.ItemViewModel

abstract class BaseItemViewHolder<T : ItemViewModel, B : ViewDataBinding>(view: View) :
    BaseViewHolder<B>(view) {

    var data: T?=null

    open fun setItem(data: T, binding: B) {
        this.data = data
        this.binding = binding
    }


}