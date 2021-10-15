package vn.nms.sample.presentation.ui.base.fragment

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import timber.log.Timber
import vn.nms.sample.R
import vn.nms.sample.presentation.ui.adapter.FooterLoadStateAdapter
import vn.nms.sample.presentation.ui.base.viewmodel.BaseListViewModel
import vn.nms.sample.presentation.ui.widget.PreCachingLayoutManager
import javax.inject.Inject

abstract class BaseViewModelRecyclerViewPagingFragment<B : ViewDataBinding,
        VM : BaseListViewModel<A>,
        A : PagingDataAdapter<*, *>> : BaseViewModelFragment<B, VM>(),
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var adapter: A

    protected var isRefreshing: Boolean = false

    var recycleView: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    var layoutManager: RecyclerView.LayoutManager? = null

    private var errorState: LoadState.Error? = null

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
        adapter.addLoadStateListener { loadState ->
            setProgressLoading(loadState.refresh is LoadState.Loading)
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }

            errorState?.let {
                Timber.d("errorState: ${it.error.message}")
                if (hasHandleErrorState() && this.errorState != errorState) {
                    this.errorState = errorState
                    viewModel.stateLiveData.setValueIfNew(
                        viewModel.errorManager.getErrorState(it.error)
                    )
                }
            }
        }
        getRecyclerView()?.adapter = adapter.withLoadStateFooter(
            footer = FooterLoadStateAdapter()
        )
    }

    open fun hasHandleErrorState(): Boolean = true

    override fun onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true
            doRefresh()
        }
    }

    open fun doRefresh() {
        viewModel.refresh()
    }

    open fun createLayoutManager(): RecyclerView.LayoutManager = PreCachingLayoutManager(activity)

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