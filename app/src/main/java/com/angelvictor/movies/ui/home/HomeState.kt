package com.angelvictor.movies.ui.home

import android.Manifest
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.angelvictor.movies.ui.common.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildHomeState(
    activity: FragmentActivity = requireActivity(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: RequestPermission = RequestPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) = HomeState(activity, scope, navController, locationPermissionRequester)

class HomeState(
    private val activity: FragmentActivity,
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val locationPermissionRequester: RequestPermission
) {

    fun openMovieList(category: Category) {
        navController.navigate(HomeFragmentDirections.actionHomeToBillboard(category))
    }

    fun checkLocationPermission(afterRequest: (PermissionState) -> Unit) {
        scope.launch {
            val permissionState = locationPermissionRequester.request()
            afterRequest(permissionState)
        }
    }

    fun showAlertDeniedLocation(category: Category) {
        Dialog.showAlertLocation(
            context = activity,
            actionPositive = { activity.openSettingsScreen() },
            actionNegative = { openMovieList(category) }
        )
    }

}