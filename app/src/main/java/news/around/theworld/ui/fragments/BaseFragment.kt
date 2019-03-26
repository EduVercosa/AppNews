package news.around.theworld.ui.fragments

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseFragment: Fragment(){

    protected var compositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    abstract fun getName(): String
}