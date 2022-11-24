package com.angelvictor.movies.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.angelvictor.movies.R
import com.angelvictor.movies.data.database.MovieDao
import com.angelvictor.movies.ui.MainActivity
import com.angelvictor.movies.ui.RecyclerViewItemCountAssertion.Companion.withItemCount
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var movieDao: MovieDao

    @Before
    fun setUp() {
        hiltRule.inject()
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
}