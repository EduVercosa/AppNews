package news.around.theworld

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class RecyclerViewScrollListener (
      layoutManager: LinearLayoutManager
    , private val loadMore: (page: Int) -> Unit = {}
    , private val floatingButtonVisibility: (shouldShow: Boolean) -> Unit)
    : RecyclerView.OnScrollListener(){

    private var visibleThreshold = 5
    private var currentPage = 1
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0
    var thresholdShowFloatingButton: Int = 10

    private var mLayoutManager: RecyclerView.LayoutManager = layoutManager

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount

        when (mLayoutManager) {
            is LinearLayoutManager -> lastVisibleItemPosition =
                (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        if(lastVisibleItemPosition > thresholdShowFloatingButton){
            floatingButtonVisibility.invoke(true)
        }else{
            floatingButtonVisibility.invoke(false)
        }

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            loadMore.invoke(currentPage)
            loading = true
        }
    }

}