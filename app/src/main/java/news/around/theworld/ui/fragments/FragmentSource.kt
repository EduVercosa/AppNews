package news.around.theworld.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.View
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import news.around.theworld.RecyclerViewScrollListener
import news.around.theworld.model.Source
import news.around.theworld.ui.adapters.SourcesRecyclerViewAdapter
import news.around.theworld.ui.interfaces.SwitchFragment
import news.around.theworld.ui.viewmodel.SourceViewModel
import news.around.theworld.ui.viewmodel.viewstate.SourceViewState
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentSource : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val sourceViewModel: SourceViewModel by viewModel()

    private val scrollListener: RecyclerViewScrollListener by lazy {
        RecyclerViewScrollListener(
            layoutManager
            , floatingButtonVisibility = this::onChangeFloatingButtonVisibility
        )
    }

    private lateinit var adapterSources: SourcesRecyclerViewAdapter

    private var switchFragment: SwitchFragment? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            switchFragment = context as SwitchFragment
        } catch (exception: Exception) {
            Log.i(getName(), "Should implements SwitchFragment interface")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeToRefresh.setOnRefreshListener(this)
        sourceViewModel.getSources()
    }

    override fun onResume() {
        super.onResume()
        observingSourceViewStateChanging()
    }

    private fun observingSourceViewStateChanging() {
        addDisposable(sourceViewModel.sourceViewState()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                updateScreen(result)
            }, {})
        )
    }

    private fun updateScreen(viewState: SourceViewState) {
        hideEmptyState()
        when (viewState) {
            is SourceViewState.Loading -> swipeToRefresh.isRefreshing = true

            is SourceViewState.Success -> {
                swipeToRefresh.isRefreshing = false
                adapterSources = SourcesRecyclerViewAdapter(
                    viewState.list,
                    this::seeArticlesAboutSelectedSource
                )
                scrollListener.thresholdShowFloatingButton = 7
                newsRecyclerView.addOnScrollListener(scrollListener)
                newsRecyclerView.adapter = adapterSources
            }
            is SourceViewState.Error -> {
                swipeToRefresh.isRefreshing = false
                Toast.makeText(context, viewState.message, Toast.LENGTH_SHORT).show()
                showEmptyState()
            }
        }
    }

    private fun seeArticlesAboutSelectedSource(selectedSource: Source) {
        switchFragment?.switchFragment(FragmentArticle.newInstance(selectedSource.id))
    }

    override fun onRefresh() {
        sourceViewModel.getSources()
    }

    override fun getName(): String = this.javaClass.simpleName
}