package vn.nms.sample.presentation.ui.features.splash

import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.data.extensions.navigateActivity
import vn.nms.sample.databinding.ActivitySplashBinding
import vn.nms.sample.domain.model.intent.SplashIntent
import vn.nms.sample.presentation.ui.base.activity.BaseViewModelActivity
import vn.nms.sample.presentation.ui.features.init.InitActivity
import vn.nms.sample.presentation.ui.features.main.MainActivity

@AndroidEntryPoint
class SplashActivity : BaseViewModelActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun createViewModelClass() = SplashViewModel::class.java

    override fun getLayoutResource() = R.layout.activity_splash

    override fun getScreenName(): String? = null

    override fun initObserver() {
        if (isTaskRoot) {
            viewModel.intent.observe(this) {
                goToNextStep(it)
            }
        } else {
            goToNextStep(SplashIntent.OpenHome)
            return
        }
    }


    private fun goToNextStep(intentData: SplashIntent) {
        if (intentData == SplashIntent.OpenHome) {
            navigateActivity<MainActivity> {
                setupNextScreenIntent(this)
            }
        } else {
            navigateActivity<InitActivity> {
                setupNextScreenIntent(this)
            }
        }
        finish()
    }

    private fun setupNextScreenIntent(newIntent: Intent) {
        if (intent.data != null && intent?.dataString?.contains(getString(R.string.app_scheme)) == true) {
            newIntent.data = this@SplashActivity.intent.data
        } else if (intent.extras?.containsKey("google.message_id") == true) {
            val deepLinkIntent = createDeepLinkIntent()
            newIntent.action = deepLinkIntent.action
            newIntent.data = deepLinkIntent.data
        }
        newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }

    private fun createDeepLinkIntent(): Intent {
        return Intent()
    }

}