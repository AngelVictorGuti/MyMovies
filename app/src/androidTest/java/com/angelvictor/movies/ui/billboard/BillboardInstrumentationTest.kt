package com.angelvictor.movies.ui.billboard

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.angelvictor.movies.R
import com.angelvictor.movies.data.remote.RemoteDataSource
import com.angelvictor.movies.data.server.MockWebServerRule
import com.angelvictor.movies.data.server.fromJson
import com.angelvictor.movies.ui.MainActivity
import com.angelvictor.movies.ui.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class BillboardInstrumentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule(order = 3)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("popular.json")
        )

        hiltRule.inject()

        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun click_a_movie_navigates_to_detail() = runTest {
        onView(withId(R.id.rvCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1, ViewActions.click()
                )
            )

        remoteDataSource.getPopularMovies("ES")

        onView(withId(R.id.rvBillboard))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, ViewActions.click()
                )
            )

        onView(withId(R.id.toolbarDetail))
            .check(matches(hasDescendant(withText("Black Adam"))))

    }
}