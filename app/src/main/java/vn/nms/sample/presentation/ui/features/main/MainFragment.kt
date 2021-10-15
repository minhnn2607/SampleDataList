package vn.nms.sample.presentation.ui.features.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import vn.nms.sample.R
import vn.nms.sample.databinding.FragmentMainBinding
import vn.nms.sample.presentation.ui.base.fragment.BaseViewModelFragment
import vn.nms.sample.presentation.utils.DeeplinkValidator
import vn.nms.sample.presentation.utils.navigation.data.NavigateOnceDeeplinkRequest
import vn.nms.sample.presentation.utils.navigation.extensions.setupMultipleBackStackBottomNavigation

@AndroidEntryPoint
class MainFragment : BaseViewModelFragment<FragmentMainBinding, MainViewModel>() {

    private var currentNavController: NavController? = null

    override fun keepInstance() = true

    override fun createViewModelClass() = MainViewModel::class.java

    override fun getLayoutResource() = R.layout.fragment_main

    override fun initialize(view: View, ctx: Context?) {
        binding.botNavigation.itemIconTintList = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            setupBottomNavigationBar()
        }
    }

    private fun setupBottomNavigationBar() {
        setupMultipleBackStackBottomNavigation(
            navGraphIds = NAV_GRAPH_IDS,
            containerId = R.id.nav_tab_fragment,
            bottomNavigationView = binding.botNavigation,
            validatedRequest = ::validateDeeplink,
            onControllerChange = ::onControllerChange
        )
    }

    private fun validateDeeplink(originalRequest: NavigateOnceDeeplinkRequest): NavigateOnceDeeplinkRequest {
        val validateDeeplink = DeeplinkValidator().validateDeeplink(originalRequest.deeplink)
        return originalRequest.copy(deeplink = validateDeeplink)
    }

    private fun onControllerChange(navController: NavController) {
        currentNavController = navController
//        navController.addOnDestinationChangedListener { controller, destination, args ->
//        }
    }

    fun doOnBackPressed() {
        if (currentNavController?.navigateUp() == false) {
            if (binding.botNavigation.selectedItemId == R.id.nav_home) {
                activity?.finish()
            } else {
                binding.botNavigation.selectedItemId = R.id.nav_home
            }
        }
    }

    companion object {
        private val NAV_GRAPH_IDS = listOf(
            R.navigation.nav_home, R.navigation.nav_setting
        )
    }

}