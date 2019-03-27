package news.around.theworld.repository

import android.util.Log
import io.reactivex.Single
import news.around.theworld.model.ArticleList
import news.around.theworld.model.SourceList
import news.around.theworld.repository.apis.NewsApi

class NewsRepository(private var service: NewsApi){

    fun getSources(): Single<SourceList> {
        return service.getSources()
    }

    fun getArticles(sourceId: String, page: Int): Single<ArticleList>{
        return service.getArticles(sourceId, page)
    }
}