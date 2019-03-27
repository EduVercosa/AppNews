package news.around.theworld.ui.fragments

import android.support.v4.app.Fragment
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseFragment: Fragment(){

    private var compositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    override fun onPause() {
        compositeDisposable.dispose()
        super.onPause()
    }

    abstract fun getName(): String
}