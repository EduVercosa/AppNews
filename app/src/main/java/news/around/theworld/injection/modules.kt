package news.around.theworld.injection

import news.around.theworld.executors.SchedulerExecutors
import news.around.theworld.repository.BaseRepository
import news.around.theworld.repository.NewsRepository
import news.around.theworld.repository.apis.NewsApi
import news.around.theworld.ui.viewmodel.ArticleViewModel
import news.around.theworld.ui.viewmodel.SourceViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun newsModule() = module {
    viewModel { ArticleViewModel(repository = get(), schedulers = get()) }
    viewModel { SourceViewModel(repository = get(), schedulers = get()) }
    single { NewsRepository(service = get()) }
    single { BaseRepository.get(NewsApi::class) }
    single { SchedulerExecutors()}
}