package news.around.theworld.executors

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerExecutors(
    private val background: Scheduler = Schedulers.io()
    , private val mainThread: Scheduler = AndroidSchedulers.mainThread()
) {

    fun background() = background

    fun mainThread() = mainThread

}