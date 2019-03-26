package news.around.theworld.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import news.around.theworld.R
import news.around.theworld.extension.startFragment
import news.around.theworld.ui.fragments.BaseFragment
import news.around.theworld.ui.fragments.FragmentSource
import news.around.theworld.ui.interfaces.SwitchFragment

class MainActivity : AppCompatActivity(), SwitchFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        startFragment(FragmentSource())
    }

    override fun switchFragment(fragment: BaseFragment) {
        startFragment(fragment)
    }
}

