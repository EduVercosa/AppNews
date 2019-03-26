package news.around.theworld.model

import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class Article(
      @SerializedName("author") val author: String
    , @SerializedName("title") val title: String
    , @SerializedName("description") val description: String
    , @SerializedName("url") val url: String
    , @SerializedName("urlToImage") val urlToImage: String
    , @SerializedName("publishedAt") val publishedAt: String
    , @SerializedName("content") val content: String
){
    fun getPublishedDay(): String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val dateFormatFinal = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            val date = dateFormat.parse(publishedAt)
            return dateFormatFinal.format(date)
        } catch (e: ParseException) {
            ""
        }
    }
}