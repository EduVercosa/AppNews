package news.around.theworld.ui.viewmodel

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import news.around.theworld.executors.SchedulerExecutors
import news.around.theworld.model.SourceList
import news.around.theworld.repository.NewsRepository
import news.around.theworld.ui.viewmodel.viewstate.SourceViewState
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito


class SourceViewModelTest {

    private val repository = Mockito.mock(NewsRepository::class.java)

    private val executors: SchedulerExecutors = SchedulerExecutors(Schedulers.trampoline(), Schedulers.trampoline())

    private val sourceViewModel = SourceViewModel(repository, executors)

    @Test
    fun sourceViewState() {


    }

    @Test
    fun getSources() {
        val listSources: SourceList = Mockito.mock(SourceList::class.java)
        Mockito.`when`(repository.getSources()).thenReturn(Single.just(listSources))

        val viewSateObserver = sourceViewModel.sourceViewState().test()

        //At this point, the cache couldn't be initialized
        Assert.assertFalse(sourceViewModel.hasCache())

        sourceViewModel.getSources()

        val listViewSate = viewSateObserver.values()

        //Loading was called
        Assert.assertEquals(true, (listViewSate[0] is SourceViewState.Loading))

        //Content from server was loaded
        Assert.assertEquals(true, (listViewSate[1] is SourceViewState.Success))

        //Now, we have to have cache
        Assert.assertTrue(sourceViewModel.hasCache())

        viewSateObserver.dispose()
    }

}