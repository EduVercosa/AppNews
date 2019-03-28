package news.around.theworld

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import news.around.theworld.repository.apis.NewsApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class BaseTest{

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    protected fun buildRepositoryMock(httpCode: Int, mockResponse: String): NewsApi {
        val response = MockResponse()
            .setResponseCode(httpCode)
            .setBody(mockResponse)

        mockWebServer.enqueue(response)

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()

        return retrofit.create(NewsApi::class.java)
    }
}