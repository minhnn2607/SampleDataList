package vn.nms.sample.presentation.ui.features.init

import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.presentation.ui.base.activity.BaseActivity

@AndroidEntryPoint
class InitActivity : BaseActivity() {
    override fun getLayoutResource() = R.layout.activity_init

    override fun getScreenName(): String?  = null

//    override fun onBackPressed() {
//        if (getBackStackCount() == 1){
//            finish()
//        } else {
//            super.onBackPressed()
//        }
//    }

    private fun getBackStackCount(): Int {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return navHostFragment?.childFragmentManager?.backStackEntryCount?:0
    }
}