package news.around.theworld.extension

import android.support.v7.app.AppCompatActivity
import news.around.theworld.R
import news.around.theworld.ui.fragments.BaseFragment


fun AppCompatActivity.startFragment(fragment: BaseFragment
                                    , container: Int? = R.id.container_fragment) {
    supportFragmentManager.beginTransaction()
        .add(container!!, fragment, fragment.getName())
        .addToBackStack(fragment.getName())
        .commit()
}