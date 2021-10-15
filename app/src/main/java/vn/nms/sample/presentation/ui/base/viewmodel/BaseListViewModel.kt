package vn.nms.sample.presentation.ui.base.viewmodel

import androidx.annotation.CallSuper
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListViewModel<A : RecyclerView.Adapter<*>>(savedStateHandle: SavedStateHandle? = null) :
    BaseViewModel(savedStateHandle) {
    var isLoading = false

    @Nullable
    lateinit var adapter: A

    @CallSuper
    open fun initAdapter(@NonNull adapter: A) {
        this.adapter = adapter
    }

    abstract fun fetchData()

    open fun refresh() {
        fetchData()
    }
}