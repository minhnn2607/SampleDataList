package vn.nms.sample.presentation.ui.base.fragment

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import vn.nms.sample.R
import vn.nms.sample.presentation.ui.base.viewmodel.BaseListViewModel
import javax.inject.Inject

abstract class BaseViewModelRecyclerViewFragment<B : ViewDataBinding,
        VM : BaseListViewModel<A>,
        A : RecyclerView.Adapter<*>> : BaseViewModelFragment<B, VM>(),
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var adapter: A

    protected var isRefreshing: Boolean = false

    var recycleView: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    var layoutManager: RecyclerView.LayoutManager? = null

    override fun initialize(view: View, ctx: Context?) {
        recycleView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        if (hasStableIds()) {
            adapter.setHasStableIds(true)
        }
        viewModel.initAdapter(adapter)
        layoutManager = createLayoutManager()
        if (recycleView?.tag != "init") {
            recycleView?.let {
                it.layoutManager = layoutManager
                it.itemAnimator = DefaultItemAnimator()
                it.adapter = adapter
                it.setHasFixedSize(hasFixedSize())
                it.tag = "init"
            }
        }
        swipeRefreshLayout?.let {
            it.setOnRefreshListener(this)
            it.setColorSchemeResources(
                R.color.material_amber_700,
                R.color.material_blue_700,
                R.color.material_purple_700,
                R.color.material_lime_700
            )
        }
    }

    override fun onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true
            doRefresh()
        }
    }

    open fun doRefresh() {
        viewModel.refresh()
    }

    open fun createLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(activity)

    open fun hasFixedSize() = false

    open fun hasStableIds() = false

    override fun setLoading(loading: Boolean) {
        setProgressLoading(loading)
    }

    private fun setProgressLoading(loading: Boolean) {
        if (!loading) {
            swipeRefreshLayout?.isRefreshing = false
            isRefreshing = false
        } else {
            if (adapter.itemCount == 0) {
                swipeRefreshLayout?.isRefreshing = true
            }
        }
    }

    fun getRecyclerView(): RecyclerView? = recycleView
}