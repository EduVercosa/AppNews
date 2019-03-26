package news.around.theworld.repository.apis

import io.reactivex.Single
import news.around.theworld.model.ArticleList
import news.around.theworld.model.SourceList
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi{

    @GET("v2/sources")
    fun getSources(): Single<SourceList>

    @GET("v2/everything")
    fun getArticles(
          @Query("sources") sourceId: String
        , @Query("page") page: Int)
            :Single<ArticleList>
}