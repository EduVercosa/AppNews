package news.around.theworld.ui.viewmodel

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import news.around.theworld.executors.SchedulerExecutors
import news.around.theworld.model.ArticleList
import news.around.theworld.repository.NewsRepository
import news.around.theworld.ui.viewmodel.viewstate.ArticleViewState

class ArticleViewModel(
     private var repository: NewsRepository
    ,private var schedulers: SchedulerExecutors
): BaseViewModel() {

    private val articleViewRelay = BehaviorSubject.create<ArticleViewState>()

    private var nextPage: Int = 1

    fun articleViewState(): Observable<ArticleViewState> = articleViewRelay.hide()

    fun getArticles(sourceId: String, page: Int = 1, isLoadingMore: Boolean = false) {
        this.nextPage = page
        articleViewRelay.onNext(ArticleViewState.Loading)
        addDisposable(
            repository
                .getArticles(sourceId, nextPage)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.mainThread())
                .subscribe(
                    { result -> onArticlesLoadedSuccessfully(result, isLoadingMore) },
                    { error -> onLoadArticlesError(error) })
        )
    }

    private fun onArticlesLoadedSuccessfully(articleList: ArticleList, isLoadingMore: Boolean = false) {
        if (isLoadingMore) {
            articleViewRelay.onNext(ArticleViewState.LoadingMore(articleList))
        } else {
            articleViewRelay.onNext(ArticleViewState.Success(articleList))
        }
    }

    private fun onLoadArticlesError(throwable: Throwable) {
        articleViewRelay.onNext(ArticleViewState.Error("Ops! our servers aren't available!"))
    }
}