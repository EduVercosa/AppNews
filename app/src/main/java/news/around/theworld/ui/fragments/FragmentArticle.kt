package news.around.theworld.ui.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_articles.*
import news.around.theworld.R
import news.around.theworld.RecyclerViewScrollListener
import news.around.theworld.ui.adapters.ArticlesRecyclerViewAdapter
import news.around.theworld.ui.viewmodel.ArticleViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FragmentArticle : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val articleViewModel: ArticleViewModel by sharedViewModel()

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    private val articlesRecyclerView: RecyclerView by lazy {
        article_recycler_view.layoutManager = layoutManager
        article_recycler_view
    }

    private val swipeToRefresh: SwipeRefreshLayout by lazy {
        refresh_layout.setOnRefreshListener(this)
        refresh_layout
    }

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articlesRecyclerView.adapter = adapterArticles
        setScrollListener()
        //observerLoadArticles()
        //observerLoadMoreArticle()
    }

    private fun getBundle(): String? {
        return arguments?.getString(BUNDLE)
    }

//    private fun observerLoadArticles() {
//        getBundle()?.let {
//            articleViewModel.onArticleResult(it).observe(this, Observer { result ->
//                result?.let {
//                    Log.d("mytag","aaa "+it.isLoading)
//                    adapterArticles.update(it)
//                    swipeToRefresh.isRefreshing = false
//                }
//            })
//        }
//    }
//
//    private fun observerLoadMoreArticle() {
//        articleViewModel.loadMoreArticlesResult().observe(this, Observer { result ->
//            result?.let {
//                Log.d("mytag", "isLoading " + it.isLoading)
//                swipeToRefresh.isRefreshing = it.isLoading
//                adapterArticles.update(it.data)
//            }
//        })
//    }

    private fun setScrollListener() {
        scrollListener = object : RecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int) {
                Log.d("mytag", "page " + page)
                getBundle()?.let {
                    articleViewModel.loadMoreArticle(it, page)
                }
            }
        }
        articlesRecyclerView.addOnScrollListener(scrollListener)
    }

    override fun onRefresh() {
       // observerLoadArticles()
    }

    override fun getName(): String = this.javaClass.simpleName
}