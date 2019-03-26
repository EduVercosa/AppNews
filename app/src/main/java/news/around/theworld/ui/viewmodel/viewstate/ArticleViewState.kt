package news.around.theworld.ui.viewmodel.viewstate

import news.around.theworld.model.ArticleList


sealed class ArticleViewState{
    object Loading: ArticleViewState()
    class Success(val data: ArticleList): ArticleViewState()
    class Error(val message: String): ArticleViewState()
}