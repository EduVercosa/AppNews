package news.around.theworld.model

import com.google.gson.annotations.SerializedName


data class ArticleList(@SerializedName("articles") val articleList: List<Article>
                       ,var isLoading: Boolean = false) {

}