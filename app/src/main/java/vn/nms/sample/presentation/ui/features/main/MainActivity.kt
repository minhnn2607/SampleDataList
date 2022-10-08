package vn.nms.sample.presentation.ui.features.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.domain.manager.UserManager
import vn.nms.sample.presentation.ui.base.activity.BaseActivity
import vn.nms.sample.presentation.utils.navigation.DeeplinkNavigationPolicy
import vn.nms.sample.presentation.utils.navigation.extensions.handleDeeplinkIntent
import vn.nms.sample.presentation.utils.navigation.extensions.setUpDeeplinkNavigationBehavior
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var userManager: UserManager

    override fun getLayoutResource() = R.layout.activity_main

    override fun getScreenName(): String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDeeplinkNavigationBehavior(
            navHostFragmentId = R.id.nav_host_fragment,
            primaryFragmentId = R.id.mainFragment,
            fragmentBackStackBehavior = TOP_LEVEL_FRAGMENT_BEHAVIOR,
            graphId = R.navigation.nav_main
        )
        handleDeeplinkIntent()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeeplinkIntent()
    }

//    override fun onBackPressed() {
//        val foregroundFragment = getForegroundFragment()
//        if (foregroundFragment is MainFragment) {
//            foregroundFragment.doOnBackPressed()
//        } else {
//            super.onBackPressed()
//        }
//    }

    private fun getForegroundFragment(): Fragment? {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return navHostFragment?.childFragmentManager?.fragments?.firstOrNull()
    }

    companion object {
        val TOP_LEVEL_FRAGMENT_BEHAVIOR: Map<Int, DeeplinkNavigationPolicy> = mapOf(
            R.id.homeFragment to DeeplinkNavigationPolicy.EXIT_AND_NAVIGATE
        )
    }

}