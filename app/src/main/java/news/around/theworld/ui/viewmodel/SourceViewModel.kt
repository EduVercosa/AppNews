package news.around.theworld.ui.viewmodel

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import news.around.theworld.executors.SchedulerExecutors
import news.around.theworld.model.SourceList
import news.around.theworld.repository.NewsRepository
import news.around.theworld.ui.viewmodel.viewstate.SourceViewState

class SourceViewModel(
     private var repository: NewsRepository
    ,private var schedulers: SchedulerExecutors
): BaseViewModel() {

    private lateinit var memoryCache: SourceViewState.MemoryCache

    private val sourceViewRelay = BehaviorSubject.create<SourceViewState>()

    fun sourceViewState(): Observable<SourceViewState> = sourceViewRelay.hide()

    fun getSources() {
        if (!sourceViewRelay.hasValue()) {
            sourceViewRelay.onNext(SourceViewState.Loading)
        }
        if (hasCache()) {
            sourceViewRelay.onNext(SourceViewState.Success(memoryCache.cache))
        } else {
            addDisposable(
                repository
                    .getSources()
                    .subscribeOn(schedulers.background())
                    .observeOn(schedulers.mainThread())
                    .subscribe({ success -> onSourcesSuccess(success) }
                        , { error -> onSourceError(error) })
            )
        }
    }

    private fun onSourcesSuccess(sourceList: SourceList) {
        memoryCache = SourceViewState.MemoryCache(sourceList)
        sourceViewRelay.onNext(SourceViewState.Success(sourceList))
    }

    fun hasCache(): Boolean {
        return try {
             memoryCache.cache != null
        } catch (e: UninitializedPropertyAccessException) {
             false
        }

    }

    private fun onSourceError(throwable: Throwable) {
        sourceViewRelay.onNext(SourceViewState.Error("Ops! something went wrong"))
    }
}