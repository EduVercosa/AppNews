package news.around.theworld.ui.viewmodel

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import news.around.theworld.executors.SchedulerExecutors
import news.around.theworld.model.SourceList
import news.around.theworld.repository.NewsRepository
import news.around.theworld.ui.viewmodel.viewstate.SourceViewState
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class SourceViewModelTest {

    private val repository = Mockito.mock(NewsRepository::class.java)

    private val executors: SchedulerExecutors = SchedulerExecutors(Schedulers.trampoline(), Schedulers.trampoline())

    private lateinit var sourceViewModel: SourceViewModel

    @Before
    fun setup(){
        sourceViewModel = SourceViewModel(repository, executors)
    }

    @Test
    fun `when the getSources is called, show loading`() {
        val listSources: SourceList = Mockito.mock(SourceList::class.java)
        Mockito.`when`(repository.getSources()).thenReturn(Single.just(listSources))

        val viewSateObserver = sourceViewModel.sourceViewState().test()

        sourceViewModel.getSources()

        val listViewSate = viewSateObserver.values()

        Assert.assertEquals(true, (listViewSate[0] is SourceViewState.Loading))

        viewSateObserver.dispose()
    }

    @Test
    fun `when the getSources is called for the second time, does not show loading`() {
        val listSources: SourceList = Mockito.mock(SourceList::class.java)
        Mockito.`when`(repository.getSources()).thenReturn(Single.just(listSources))

        sourceViewModel.getSources()

        val viewSateObserver = sourceViewModel.sourceViewState().test()

        sourceViewModel.getSources()

        val listViewSate = viewSateObserver.values()

        Assert.assertEquals(true, (listViewSate[0] is SourceViewState.Success))
        Assert.assertEquals(true, (listViewSate[1] is SourceViewState.Success))

        viewSateObserver.dispose()
    }

    @Test
    fun `when the getSources is called, and return success, then return view sate success`() {
        val listSources: SourceList = Mockito.mock(SourceList::class.java)
        Mockito.`when`(repository.getSources()).thenReturn(Single.just(listSources))

        val viewSateObserver = sourceViewModel.sourceViewState().test()

        sourceViewModel.getSources()

        val listViewSate = viewSateObserver.values()

        Assert.assertEquals(true, (listViewSate[1] is SourceViewState.Success))

        viewSateObserver.dispose()
    }

    @Test
    fun `when the getSources is called, and before return, cache must be empty`() {
        val listSources: SourceList = Mockito.mock(SourceList::class.java)
        Mockito.`when`(repository.getSources()).thenReturn(Single.just(listSources))

        Assert.assertFalse(sourceViewModel.hasCache())

        sourceViewModel.getSources()
    }

    @Test
    fun `when the getSources is called, and return success, cache must be filled`() {
        val listSources: SourceList = Mockito.mock(SourceList::class.java)
        Mockito.`when`(repository.getSources()).thenReturn(Single.just(listSources))

        sourceViewModel.getSources()

        Assert.assertTrue(sourceViewModel.hasCache())
    }

    @Test
    fun `when the getSources is called, and got a error, then show error`() {
        Mockito.`when`(repository.getSources()).thenReturn(Single.error(RuntimeException("")))

        val viewSateObserver = sourceViewModel.sourceViewState().test()

        sourceViewModel.getSources()

        val listViewSate = viewSateObserver.values()

        Assert.assertEquals(true, (listViewSate[1] is SourceViewState.Error))

        viewSateObserver.dispose()
    }

    @Test
    fun `when the getSources is called, and got a error, then cache must be empty`() {
        Mockito.`when`(repository.getSources()).thenReturn(Single.error(RuntimeException("")))

        sourceViewModel.getSources()

        Assert.assertFalse(sourceViewModel.hasCache())
    }
}