package news.around.theworld.ui.viewmodel.viewstate

import news.around.theworld.model.SourceList


sealed class SourceViewState{
    object Loading: SourceViewState()
    class Success(val data: SourceList): SourceViewState()
    class Error(val message: String): SourceViewState()
}