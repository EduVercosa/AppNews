package news.around.theworld.ui.viewmodel

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test



class ArticleViewModelTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer

        val url = "dfdf/"
//        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(mResultList)))
//        val retrofit = Retrofit.Builder()
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(mMockWebServer.url(url))
//            .build()
//        val remoteDataSource = RemoteDataSource(retrofit)

    }

    @After
    fun tearDown() {
    }

    @Test
    fun articleViewState() {
    }

    @Test
    fun getArticles() {
    }
}