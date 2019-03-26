package news.around.theworld

import android.app.Application
import android.content.Context
import news.around.theworld.injection.newsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class NewsApplication : Application(){

    companion object {
        private lateinit var context: Context

        @JvmStatic
        fun getContext() = context
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(newsModule())
        }
        context = applicationContext
    }
}