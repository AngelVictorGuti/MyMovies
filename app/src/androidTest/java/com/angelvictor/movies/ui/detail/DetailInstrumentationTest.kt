package com.angelvictor.movies.ui.detail

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.angelvictor.movies.R
import com.angelvictor.movies.data.database.MovieDao
import com.angelvictor.movies.data.database.MovieDataSource
import com.angelvictor.movies.data.remote.RemoteDataSource
import com.angelvictor.movies.data.server.MockWebServerRule
import com.angelvictor.movies.data.server.fromJson
import com.angelvictor.movies.sampleMovie
import com.angelvictor.movies.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class DetailInstrumentationTest {

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
    lateinit var remoteDataSource: RemoteDataSource

    @Inject
    lateinit var localDataSource: MovieDataSource

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("popular.json")
        )
        hiltRule.inject()
    }

    @Test
    fun click_favorite_button() = runTest {
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

        onView(withId(R.id.ivFavorite))
            .perform(
                ViewActions.click()
            )

        val result = localDataSource.isEmpty()

        Assert.assertFalse(result)

    }

    @Test
    fun go_home_screen_after_remove_all_favorites() = runTest {
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

        onView(withId(R.id.ivFavorite))
            .perform(
                ViewActions.click()
            )
        Espresso.pressBack()
        Espresso.pressBack()

        onView(withId(R.id.rvCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    4, ViewActions.click()
                )
            )

        onView(withId(R.id.rvBillboard))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, ViewActions.click()
                )
            )

        onView(withId(R.id.ivFavorite))
            .perform(
                ViewActions.click()
            )

        Espresso.pressBack()

        onView(withId(R.id.rvCategories)).check { view, _ ->
            Assert.assertEquals((view as? RecyclerView)?.adapter?.itemCount, 4)
        }

    }

    @Test
    fun go_list_screen_after_on_back_pressed_with_remote_movies() = runTest {
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

        onView(withId(R.id.ivFavorite))
            .perform(
                ViewActions.click()
            )

        Espresso.pressBack()

        onView(withId(R.id.rvBillboard)).check { view, _ ->
            Assert.assertTrue(((view as? RecyclerView)?.adapter?.itemCount ?: 0) > 0)
        }

    }

    @Test
    fun go_list_screen_after_on_back_pressed_with_local_movies() = runTest {
        onView(withId(R.id.rvCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1, ViewActions.click()
                )
            )

        remoteDataSource.getPopularMovies("ES")
        localDataSource.saveMovie(sampleMovie)

        onView(withId(R.id.rvBillboard))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, ViewActions.click()
                )
            )

        onView(withId(R.id.ivFavorite))
            .perform(
                ViewActions.click()
            )
        Espresso.pressBack()
        Espresso.pressBack()

        onView(withId(R.id.rvCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    4, ViewActions.click()
                )
            )

        onView(withId(R.id.rvBillboard))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, ViewActions.click()
                )
            )

        onView(withId(R.id.ivFavorite))
            .perform(
                ViewActions.click()
            )

        Espresso.pressBack()

        onView(withId(R.id.rvBillboard)).check { view, _ ->
            Assert.assertTrue(((view as? RecyclerView)?.adapter?.itemCount ?: 0) > 0)
        }

    }

}