package com.angelvictor.movies.ui.home

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
import com.angelvictor.movies.data.database.MovieDao
import com.angelvictor.movies.data.remote.RemoteDataSource
import com.angelvictor.movies.data.server.MockWebServerRule
import com.angelvictor.movies.data.server.fromJson
import com.angelvictor.movies.ui.MainActivity
import com.angelvictor.movies.ui.OkHttp3IdlingResource
import com.angelvictor.movies.ui.RecyclerViewItemCountAssertion.Companion.withItemCount
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class HomeInstrumentationTest {

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
    lateinit var movieDao: MovieDao

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        mockWebServerRule.server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                val path = request.path
                return when {
                    path?.contains("/popular") == true -> MockResponse().fromJson("popular.json")
                    path?.contains("/now_playing") == true -> MockResponse().fromJson("now_playing.json")
                    path?.contains("/top_rated") == true -> MockResponse().fromJson("top_rated.json")
                    path?.contains("/upcoming") == true -> MockResponse().fromJson("upcoming.json")

                    else -> MockResponse().fromJson("popular.json")
                }
            }

        }

        hiltRule.inject()

        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun click_a_category_navigates_to_list() = runTest {
        onView(withId(R.id.rvCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1, ViewActions.click()
                )
            )

        onView(withId(R.id.toolbarBillboard))
            .check(matches(hasDescendant(withText("Popular"))))

    }

    @Test
    fun check_recyclerview_has_4_categories() = runTest {

        onView(withId(R.id.rvCategories))
            .check(withItemCount(4))

    }

    @Test
    fun show_popular_movies() = runTest {
        onView(withId(R.id.rvCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1, ViewActions.click()
                )
            )


        val movies = remoteDataSource.getPopularMovies("ES")

        movies.fold(
            ifLeft = {
                throw Exception(it.toString())
            },
            ifRight = {
                Assert.assertEquals(it[0].title, "Black Adam")
            }
        )
    }

    @Test
    fun show_now_playing_movies_movies() = runTest {
        onView(withId(R.id.rvCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, ViewActions.click()
                )
            )


        val movies = remoteDataSource.getNowPlayingMovies("ES")

        movies.fold(
            ifLeft = {
                throw Exception(it.toString())
            },
            ifRight = {
                Assert.assertEquals(it[1].title, "Black Panther: Wakanda Forever")
            }
        )
    }

    @Test
    fun show_top_rated_movies() = runTest {
        onView(withId(R.id.rvCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    2, ViewActions.click()
                )
            )


        val movies = remoteDataSource.getTopRatedMovies("ES")

        movies.fold(
            ifLeft = {
                throw Exception(it.toString())
            },
            ifRight = {
                Assert.assertEquals(it[2].title, "The Godfather Part II")
            }
        )
    }

    @Test
    fun show_upcoming_movies() = runTest {
        onView(withId(R.id.rvCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    3, ViewActions.click()
                )
            )


        val movies = remoteDataSource.getUpcomingMovies("ES")

        movies.fold(
            ifLeft = {
                throw Exception(it.toString())
            },
            ifRight = {
                Assert.assertEquals(it[3].title, "Aftersun")
            }
        )
    }
}