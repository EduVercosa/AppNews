package news.around.theworld.ui.activities

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import news.around.theworld.R
import news.around.theworld.extension.startFragment
import news.around.theworld.ui.fragments.BaseFragment
import news.around.theworld.ui.fragments.FragmentSource
import news.around.theworld.ui.interfaces.SwitchFragment

class MainActivity : AppCompatActivity(), SwitchFragment {

    private var configurationChanged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if(!configurationChanged) {
            startFragment(FragmentSource())
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        configurationChanged = true
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun switchFragment(fragment: BaseFragment) {
        startFragment(fragment)
    }
}

