package vn.nms.sample.presentation.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.internal.managers.FragmentComponentManager
import vn.nms.sample.R
import vn.nms.sample.data.extensions.formatDate
import vn.nms.sample.data.extensions.navigate
import vn.nms.sample.data.extensions.navigateActivity
import vn.nms.sample.data.extensions.showToast
import vn.nms.sample.domain.manager.UserManager
import vn.nms.sample.presentation.ui.features.home.HomeFragmentDirections
import vn.nms.sample.presentation.ui.features.init.InitActivity
import vn.nms.sample.presentation.ui.features.init.InitFragmentDirections
import vn.nms.sample.presentation.ui.features.main.MainActivity
import vn.nms.sample.presentation.ui.features.main.MainFragmentDirections
import java.util.*

class NavigatorHelper(private val userManager: UserManager) {

    fun getActivity(context: Context): Activity {
        return FragmentComponentManager.findActivity(context) as Activity
    }

    private fun getFragmentNavControl(context: Context): NavController {
        return getActivity(context).findNavController(R.id.nav_tab_fragment)
    }

    fun backToHome(context: Context) {
        getActivity(context)
            .findNavController(R.id.nav_host_fragment).popBackStack(R.id.mainFragment, false)
    }

    fun actionOpenMainActivity(context: Context) {
        val activity = getActivity(context)
        activity.navigateActivity<MainActivity> {
            if (activity.intent.data != null && activity.intent?.dataString?.contains(
                    activity.getString(
                        R.string.app_scheme
                    )
                ) == true
            ) {
                val intentData = activity.intent?.data
                if (intentData != null) {
                    data = intentData
                }
            }
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        activity.finish()
    }

    fun actionOpenInitActivity(context: Context) {
        val activity = getActivity(context)
        activity.navigateActivity<InitActivity> {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        activity.finish()
    }

    fun actionHomeToMovieSingleTypePaging(context: Context) {
        getFragmentNavControl(context).navigate(HomeFragmentDirections.actionHomeToMovieSingleTypePaging())
    }

    fun actionHomeToMovieMultiTypePagingNetwork(context: Context) {
        getFragmentNavControl(context).navigate(HomeFragmentDirections.actionHomeToMovieMultiTypePagingNetwork())
    }

    fun actionHomeToMovieMultiTypePagingNetworkDb(context: Context) {
        getFragmentNavControl(context).navigate(HomeFragmentDirections.actionHomeToMovieMultiTypePagingNetworkDatabase())
    }

    fun actionHomeToMovieSingleType(context: Context) {
        getFragmentNavControl(context).navigate(HomeFragmentDirections.actionHomeToMovieSingleType())
    }

    fun actionHomeToMovieMultiType(context: Context) {
        getFragmentNavControl(context).navigate(HomeFragmentDirections.actionHomeToMovieMultiType())
    }

    fun actionOpenWebView(context: Context, url: String?, title: String = "") {
        val activity = getActivity(context)
        if (activity is InitActivity) {
            actionInitToWebView(context, url = url ?: "", title = title)
        } else if (activity is MainActivity) {
            actionMainToWebView(context, url = url ?: "", title = title)
        }
    }

    fun actionInitToMember(context: Context) {
        getActivity(context).navigate(InitFragmentDirections.actionOpenMember())
    }

    private fun actionInitToWebView(context: Context, url: String, title: String = "") {
        getActivity(context).navigate(InitFragmentDirections.actionOpenWebView(url, title))
    }

    private fun actionMainToWebView(context: Context, url: String, title: String = "") {
        getActivity(context).navigate(MainFragmentDirections.actionOpenWebView(url, title))
    }

    /**
     * Deep link
     */
    private fun actionOpenDeepLink(context: Context, deeplink: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(deeplink)
        }
        context.startActivity(intent)
    }

    /**
     * Other action
     */
    fun actionShare(context: Context, shareContext: String) {
        getActivity(context).apply {
            startActivity(ShareCompat.IntentBuilder.from(this)
                .apply {
                    setText(shareContext)
                    setType("text/plain")
                }
                .createChooserIntent().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    fun showDatePickerDialog(
        context: Context,
        calendar: Calendar,
        onResult: ((dob: String) -> Unit)
    ) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    onResult.invoke(calendar.timeInMillis.formatDate("yyyy-MM-dd"))
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)

        ).show()
    }

    fun onBackPressed(context: Context) {
        getActivity(context).onBackPressed()
    }

    fun showToast(context: Context, message: String) {
        context.showToast(message)
    }

    fun navigatorCall(context: Context, phoneNumber: String?) {
        if (phoneNumber != null) {
            val intent =
                Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            context.startActivity(intent)
        }
    }

    fun finishActivity(context: Context) {
        getActivity(context).finish()
    }

    inline fun <reified T : Activity> navigateActivity(
        context: Context,
        block: Intent.() -> Unit = {}
    ) {
        getActivity(context).apply {
            startActivity(Intent(this, T::class.java).apply(block))
        }
    }
}