package news.around.theworld.ui.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.base_fragment.*
import news.around.theworld.R

abstract class BaseFragment: Fragment(){

    private var compositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    protected val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    protected val newsRecyclerView: RecyclerView by lazy {
        news_recycler_view.layoutManager = layoutManager
        news_recycler_view
    }

    protected val swipeToRefresh: SwipeRefreshLayout by lazy {
        refresh_layout
    }

    private val floatingButton: FloatingActionButton by lazy {floating_button_go_up}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.base_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floatingButton.hide()
    }

    protected fun onChangeFloatingButtonVisibility(shouldShow: Boolean){
        if(shouldShow){
            floatingButton.show()
            floatingButton.setOnClickListener {
                newsRecyclerView.smoothScrollToPosition(0) }
        }else{
            floatingButton.hide()
        }
    }

    override fun onPause() {
        compositeDisposable.dispose()
        super.onPause()
    }

    abstract fun getName(): String
}