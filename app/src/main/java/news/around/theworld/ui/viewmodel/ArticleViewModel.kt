package news.around.theworld.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import news.around.theworld.model.ArticleList
import news.around.theworld.repository.NewsRepository
import news.around.theworld.ui.viewmodel.viewstate.ArticleViewState

class ArticleViewModel(private var repository: NewsRepository) : BaseViewModel() {

    private val listArticles = MutableLiveData<ArticleList>()

    private val listMoreArticles = MutableLiveData<ArticleList>()

    private val articleViewState = MutableLiveData<ArticleViewState>()

    private val articleViewRelay = BehaviorSubject.create<ArticleViewState>()

    fun articleViewState(): Observable<ArticleViewState> = articleViewRelay.hide()

    private fun getArticles(sourceId: String, page: Int) {
        addDisposable(repository
            .getArticles(sourceId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { listArticles.value  }
            .subscribe(
                { result -> listArticles.value = result },
                { this::onSearchResultError })
        )
    }

    private fun getMoreArticles(sourceId: String, page: Int) {
        addDisposable(repository
            .getArticles(sourceId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { this::onArticlesSuccess },
                { this::onSearchResultError })
        )
    }

    private fun onArticlesSuccess(articleList: ArticleList){
        articleViewRelay.onNext(ArticleViewState.Success(articleList))
    }

    fun loadMoreArticle(sourceId: String, page: Int){
        getMoreArticles(sourceId, page)
    }

    fun onSearchResultError(throwable: Throwable) {
    }
}