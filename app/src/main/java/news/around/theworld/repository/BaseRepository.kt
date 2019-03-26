package news.around.theworld.repository

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import news.around.theworld.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class BaseRepository {

    companion object {
        private val DATE_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ss"

        fun <T : Any> get(type: KClass<T>, baseUrl: String = BuildConfig.URL_API): T {
            val gson = GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .setPrettyPrinting()
                .create()

            val okHttpBuilder = OkHttpClient.Builder()
            okHttpBuilder.readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                okHttpBuilder.addInterceptor(logging)
            }


            okHttpBuilder.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Authorization", "Bearer " + "303a19134c9247bf83247c3f4d444fee")
                    .build()
                chain.proceed(request)
            }

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(type.java)
        }

    }
}