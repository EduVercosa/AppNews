package news.around.theworld.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_sources.*
import news.around.theworld.R
import news.around.theworld.RecyclerViewScrollListener
import news.around.theworld.model.Source
import news.around.theworld.ui.adapters.SourcesRecyclerViewAdapter
import news.around.theworld.ui.interfaces.SwitchFragment
import news.around.theworld.ui.viewmodel.SourceViewModel
import news.around.theworld.ui.viewmodel.viewstate.SourceViewState
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FragmentSource: BaseFragment(), SwipeRefreshLayout.OnRefreshListener{

    private val sourceViewModel: SourceViewModel by sharedViewModel()

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    private val sourcesRecyclerView: RecyclerView by lazy {
        news_recycler_view.layoutManager = layoutManager
        news_recycler_view
    }

    private val scrollListener: RecyclerViewScrollListener by lazy {
        RecyclerViewScrollListener(layoutManager
            , floatingButtonVisibility = this::onChangeFloatingButtonVisibility)
    }

    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        source_refresh_layout
    }

    private lateinit var adapterSources: SourcesRecyclerViewAdapter

    private var switchFragment: SwitchFragment? = null

    private val floatingButton: FloatingActionButton by lazy {go_sources_up}

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            switchFragment = context as SwitchFragment
        }catch (exception: Exception){
            Log.i(getName(), "Should implements SwitchFragment interface")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sources, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener(this)
        sourceViewModel.getSources()
    }

    override fun onResume() {
        super.onResume()
        observingSourceViewStateChanging()
    }

    private fun observingSourceViewStateChanging(){
        addDisposable(sourceViewModel.sourceViewState()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
                updateScreen(result)
            },{}))
    }

    private fun updateScreen(viewState: SourceViewState){
        when(viewState){
            is SourceViewState.Loading -> swipeRefreshLayout.isRefreshing = true

            is SourceViewState.Success ->{
                swipeRefreshLayout.isRefreshing = false
                adapterSources = SourcesRecyclerViewAdapter(viewState.list,
                    this::seeArticlesAboutSelectedSource)
                scrollListener.thresholdShowFloatingButton = 7
                sourcesRecyclerView.addOnScrollListener(scrollListener)
               sourcesRecyclerView.adapter = adapterSources
            }
            is SourceViewState.Error -> {
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(context, viewState.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onChangeFloatingButtonVisibility(shouldShow: Boolean){
        if(shouldShow){
            floatingButton.show()
            floatingButton.setOnClickListener {
                sourcesRecyclerView.smoothScrollToPosition(0) }
        }else{
            floatingButton.hide()
        }
    }

    private fun seeArticlesAboutSelectedSource(selectedSource: Source){
        switchFragment?.switchFragment(FragmentArticle.newInstance(selectedSource.id))
    }

    override fun onRefresh() {
        sourceViewModel.getSources()
    }

    override fun getName(): String = this.javaClass.simpleName
}