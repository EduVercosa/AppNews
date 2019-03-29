package news.around.theworld.ui.viewmodel

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import news.around.theworld.executors.SchedulerExecutors
import news.around.theworld.model.ArticleList
import news.around.theworld.repository.NewsRepository
import news.around.theworld.ui.viewmodel.viewstate.ArticleViewState
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ArticleViewModelTest {

    private val repository = Mockito.mock(NewsRepository::class.java)

    private val executors: SchedulerExecutors = SchedulerExecutors(Schedulers.trampoline(), Schedulers.trampoline())

    private lateinit var articleViewModel: ArticleViewModel

    @Before
    fun setup(){
        articleViewModel = ArticleViewModel(repository, executors)
    }

    @Test
    fun `when the getArticles is called for the first time or loading more then show loading`() {
        val listArticles: ArticleList = Mockito.mock(ArticleList::class.java)
        Mockito.`when`(repository.getArticles(Mockito.anyString(), Mockito.anyInt()))
            .thenReturn(Single.just(listArticles))

        val viewSateObserver = articleViewModel.articleViewState().test()

        articleViewModel.getArticles(Mockito.anyString(), Mockito.anyInt())

        val listViewSate = viewSateObserver.values()

        Assert.assertEquals(true, (listViewSate[0] is ArticleViewState.Loading))

        viewSateObserver.dispose()
    }

    @Test
    fun `when the getArticles is called for loading more then should return LoadingMore`() {
        val listArticles: ArticleList = Mockito.mock(ArticleList::class.java)
        Mockito.`when`(repository.getArticles(Mockito.anyString(), Mockito.anyInt()))
            .thenReturn(Single.just(listArticles))

        val viewSateObserver = articleViewModel.articleViewState().test()

        articleViewModel.getArticles(Mockito.anyString(), Mockito.anyInt(), isLoadingMore = true)

        val listViewSate = viewSateObserver.values()
        Assert.assertEquals(true, (listViewSate[1] is ArticleViewState.LoadingMore))

        viewSateObserver.dispose()
    }

    @Test
    fun `when the getArticles is called for loading more then show loading`() {
        val listArticles: ArticleList = Mockito.mock(ArticleList::class.java)
        Mockito.`when`(repository.getArticles(Mockito.anyString(), Mockito.anyInt()))
            .thenReturn(Single.just(listArticles))

        val viewSateObserver = articleViewModel.articleViewState().test()

        articleViewModel.getArticles("", 1, isLoadingMore = true)

        val listViewSate = viewSateObserver.values()

        Assert.assertEquals(true, (listViewSate[0] is ArticleViewState.Loading))

        viewSateObserver.dispose()
    }

    @Test
    fun `when the getArticles is called for the second time, does not show loading`() {
        val listArticles: ArticleList = Mockito.mock(ArticleList::class.java)
        Mockito.`when`(repository.getArticles(Mockito.anyString(), Mockito.anyInt()))
            .thenReturn(Single.just(listArticles))

        articleViewModel.getArticles(Mockito.anyString(), Mockito.anyInt())

        val viewSateObserver = articleViewModel.articleViewState().test()

        articleViewModel.getArticles(Mockito.anyString(), Mockito.anyInt())

        val listViewSate = viewSateObserver.values()

        Assert.assertEquals(true, (listViewSate[0] is ArticleViewState.Success))

        viewSateObserver.dispose()
    }

    @Test
    fun `when the getArticles is called, and return success, then return view sate success`() {
        val listArticles: ArticleList = Mockito.mock(ArticleList::class.java)
        Mockito.`when`(repository.getArticles(Mockito.anyString(), Mockito.anyInt()))
            .thenReturn(Single.just(listArticles))

        val viewSateObserver = articleViewModel.articleViewState().test()

        articleViewModel.getArticles(Mockito.anyString(), Mockito.anyInt())

        val listViewSate = viewSateObserver.values()

        Assert.assertEquals(true, (listViewSate[1] is ArticleViewState.Success))

        viewSateObserver.dispose()
    }

    @Test
    fun `when the getArticles is called, and got a error, then show error`() {
        Mockito.`when`(repository.getArticles(Mockito.anyString(), Mockito.anyInt()))
            .thenReturn(Single.error(RuntimeException("")))

        val viewSateObserver = articleViewModel.articleViewState().test()

        articleViewModel.getArticles(Mockito.anyString(), Mockito.anyInt())

        val listViewSate = viewSateObserver.values()

        Assert.assertEquals(true, (listViewSate[1] is ArticleViewState.Error))

        viewSateObserver.dispose()
    }

}