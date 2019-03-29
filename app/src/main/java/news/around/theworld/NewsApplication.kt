package news.around.theworld

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import news.around.theworld.injection.newsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        startKoin {
            androidContext(this@NewsApplication)
            modules(newsModule())
        }
    }
}