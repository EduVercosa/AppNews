package news.around.theworld.ui.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import news.around.theworld.RecyclerViewScrollListener
import news.around.theworld.ui.adapters.ArticlesRecyclerViewAdapter
import news.around.theworld.ui.viewmodel.ArticleViewModel
import news.around.theworld.ui.viewmodel.viewstate.ArticleViewState
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentArticle : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val articleViewModel: ArticleViewModel by viewModel()

    private var adapterArticles: ArticlesRecyclerViewAdapter = ArticlesRecyclerViewAdapter()

    private lateinit var scrollListener: RecyclerViewScrollListener

    companion object {
        @JvmStatic
        private val BUNDLE = "sourceId"

        fun newInstance(sourceId: String): BaseFragment {
            val fragment = FragmentArticle()
            val bundle = Bundle()
            bundle.putString(BUNDLE, sourceId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsRecyclerView.adapter = adapterArticles
        swipeToRefresh.setOnRefreshListener(this)
        setScrollListener()
        getArticles(1)
    }

    override fun onResume() {
        super.onResume()
        observingArticleViewStateChanging()
    }

    private fun getBundle(): String? {
        return arguments?.getString(BUNDLE)
    }

    private fun observingArticleViewStateChanging(){
        addDisposable(articleViewModel.articleViewState()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState -> updateScreen(viewState)
            },{

            })
        )
    }

    private fun getArticles(page: Int = 1, isLoadingMore: Boolean = false){
        getBundle()?.let {
            articleViewModel.getArticles(it,page, isLoadingMore)
        }
    }

    private fun setScrollListener() {
        scrollListener = RecyclerViewScrollListener(layoutManager
            , this::onLoadMore, this::onChangeFloatingButtonVisibility)
        scrollListener.thresholdShowFloatingButton = 3
        newsRecyclerView.addOnScrollListener(scrollListener)
    }

    private fun onLoadMore(page: Int){
        getArticles(page = page, isLoadingMore = true)
    }

    private fun updateScreen(viewState: ArticleViewState){
        hideEmptyState()
        when(viewState){
            is ArticleViewState.Loading -> swipeToRefresh.isRefreshing = true
            is ArticleViewState.Success ->{
                swipeToRefresh.isRefreshing = false
                adapterArticles.initContent(viewState.data)
            }
            is ArticleViewState.LoadingMore ->{
                swipeToRefresh.isRefreshing = false
                adapterArticles.update(viewState.data)
            }
            is ArticleViewState.Error -> {
                swipeToRefresh.isRefreshing = false
                Toast.makeText(context, viewState.message, Toast.LENGTH_SHORT).show()
                showEmptyState()
            }
        }
    }

    override fun onRefresh() {
        getArticles(isLoadingMore = false)
    }

    override fun getName(): String = this.javaClass.simpleName
}