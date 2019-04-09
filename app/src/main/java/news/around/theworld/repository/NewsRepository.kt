package news.around.theworld.repository

import io.reactivex.Single
import news.around.theworld.model.ArticleList
import news.around.theworld.model.SourceList
import news.around.theworld.repository.apis.NewsApi

class NewsRepository(private var service: NewsApi) {

    private var cacheInMemorySources: SourceList? = null

    fun getSources(): Single<SourceList> {
        cacheInMemorySources?.let {
            return Single.just(it)
        } ?: run {
            return service.getSources().doAfterSuccess {
                cacheInMemorySources = it
            }
        }
    }

    fun getArticles(sourceId: String, page: Int): Single<ArticleList> {
        return service.getArticles(sourceId, page)
    }
}