package com.angelvictor.movies.data

import com.angelvictor.movies.data.datasource.LocationDataSource
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionsChecker
) {

    companion object {
        const val DEFAULT_REGION = "US"
    }

    suspend fun findRegion(): String {
        return if (permissionChecker.checkLocation()) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}