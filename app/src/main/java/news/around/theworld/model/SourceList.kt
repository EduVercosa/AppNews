package news.around.theworld.model

import com.google.gson.annotations.SerializedName


data class SourceList(@SerializedName("sources") val sources: List<Source>)