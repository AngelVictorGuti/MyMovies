package com.angelvictor.movies.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class LocationRepositoryTest {

    private val actualRegion = "ES"

    @Test
    fun `Returns default region when coarse permission not granted`(): Unit = runBlocking {
        val locationRepository = LocationRepository(
            locationDataSource = mock(),
            permissionChecker = mock {
                on { checkLocation() } doReturn false
            }
        )

        val region = locationRepository.findRegion()

        assertEquals(LocationRepository.DEFAULT_REGION, region)
    }

    @Test
    fun `Returns region from location data source when permission granted`(): Unit = runBlocking {
        val locationRepository = LocationRepository(
            locationDataSource = mock { onBlocking { findLastRegion() } doReturn actualRegion },
            permissionChecker = mock { on { checkLocation() } doReturn true }
        )

        val region = locationRepository.findRegion()

        assertEquals(actualRegion, region)
    }
}