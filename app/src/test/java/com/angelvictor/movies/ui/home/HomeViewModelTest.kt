package com.angelvictor.movies.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.angelvictor.movies.CoroutinesTestRule
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.usecases.DatabaseEmtpyUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var databaseEmtpyUseCase: DatabaseEmtpyUseCase

    private lateinit var vm: HomeViewModel


    @Before
    fun setup() {
        vm = HomeViewModel(databaseEmtpyUseCase)
    }

    @Test
    fun `Check that the favorites category appears if the database is not empty`() = runTest {
        whenever(databaseEmtpyUseCase()).thenReturn(false)

        vm.getCategories()
        runCurrent()

        assertEquals(
            HomeViewModel.UiState(categories = Category.values().toList()),
            vm.homeUiState.value
        )
    }

    @Test
    fun `Check that the favorites category not shown if the database is empty`() = runTest {
        whenever(databaseEmtpyUseCase()).thenReturn(true)

        vm.getCategories()
        runCurrent()

        assertEquals(
            HomeViewModel.UiState(categories = Category.values().toList().filter { it != Category.FAVORITES }),
            vm.homeUiState.value
        )
    }
}