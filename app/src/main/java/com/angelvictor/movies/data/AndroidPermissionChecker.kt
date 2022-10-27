package com.angelvictor.movies.data

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import javax.inject.Inject

class AndroidPermissionChecker @Inject constructor(private val application: Application) :
    PermissionsChecker {

    override fun checkLocation(): Boolean =
        ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
}