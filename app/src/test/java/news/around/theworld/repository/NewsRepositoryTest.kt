package news.around.theworld.repository

import news.around.theworld.BaseTest
import news.around.theworld.mock.getArticlesJsonResponseMock
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test

class NewsRepositoryTest : BaseTest() {

    @Test
    fun getSources() {

        val api = buildRepositoryMock(200, getArticlesJsonResponseMock())
        val repository = NewsRepository(api)


        val result = repository
            .getSources()
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .values()

        Assert.assertThat(result, Matchers.notNullValue())
    }

    @Test
    fun getArticles() {

        val api = buildRepositoryMock(200, getArticlesJsonResponseMock())
        val repository = NewsRepository(api)

        val result = repository
            .getArticles("", 1)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .values()

        Assert.assertThat(result, Matchers.notNullValue())
    }


}