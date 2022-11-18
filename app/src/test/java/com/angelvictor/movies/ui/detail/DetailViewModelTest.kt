package com.angelvictor.movies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.angelvictor.movies.CoroutinesTestRule
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.sampleMovie
import com.angelvictor.movies.ui.common.toUiModel
import com.angelvictor.movies.usecases.ChangeMovieFavoriteUseCase
import com.angelvictor.movies.usecases.DatabaseEmtpyUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {


    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var databaseEmtpyUseCase: DatabaseEmtpyUseCase

    @Mock
    lateinit var changeMovieFavoriteUseCase: ChangeMovieFavoriteUseCase

    private lateinit var vm: DetailViewModel

    @Before
    fun setup() {
        vm = DetailViewModel(changeMovieFavoriteUseCase, databaseEmtpyUseCase)
    }

    @Test
    fun `Show average if average is more than 5`() = runTest {

        assertTrue(vm.showAverage(5.1))
    }

    @Test
    fun `Hide average if average is less or equals than 5`() = runTest {

        assertFalse(vm.showAverage(5.0))
    }

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        vm.onUiReady(sampleMovie.toUiModel(true))

        val results = mutableListOf<DetailViewModel.UiState?>()
        val observer = Observer<DetailViewModel.UiState?> {
            results.add(it)
        }
        val job = launch {
            vm.detailState.observeForever(observer)
        }
        runCurrent()
        job.cancel()

        assertTrue(
            results.find {
                it?.movie == sampleMovie.toUiModel(true) && it.error == null
            }?.let {
                true
            } ?: false
        )
    }

    @Test
    fun `Call the use case change favorite when clicked`() = runTest {
        vm.favoriteOnClick(sampleMovie.toUiModel(true))
        runCurrent()

        verify(changeMovieFavoriteUseCase).invoke(sampleMovie)
    }

    @Test
    fun `Retrying to call the change favorite use case returns an error`() = runTest {
        whenever(changeMovieFavoriteUseCase(sampleMovie)).thenReturn(Error.Unknown("Database Error"))

        vm.onRetryChangeFavorite()
        runCurrent()
        val error = changeMovieFavoriteUseCase(sampleMovie)

        assertEquals(
            (error as Error.Unknown).message,
            Error.Unknown("Database Error").message
        )

    }

    @Test
    fun `Check if database is empty when on back pressed clicked`() = runTest {
        vm.onBackPressed({}, {})
        runCurrent()

        verify(databaseEmtpyUseCase).invoke()
    }


}