package com.angelvictor.movies.ui.billboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.right
import com.angelvictor.movies.testrules.CoroutinesTestRule
import com.angelvictor.movies.sampleMovie
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.ui.common.toUiModel
import com.angelvictor.movies.usecases.*
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
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BillboardViewModelTest {


    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase

    @Mock
    lateinit var requestNowPlayingMoviesUseCase: RequestNowPlayingMoviesUseCase

    @Mock
    lateinit var requestTopRatedMoviesUseCase: RequestTopRatedMoviesUseCase

    @Mock
    lateinit var requestUpcomingMoviesUseCase: RequestUpcomingMoviesUseCase

    @Mock
    lateinit var checkMovieIsFavoriteUseCase: CheckMovieIsFavoriteUseCase

    @Mock
    lateinit var getFavoritesMoviesUseCase: GetFavoritesMoviesUseCase

    private lateinit var vm: BillboardViewModel

    private val movies = listOf(sampleMovie.copy(id = 1))

    @Before
    fun setup() {
        vm = BillboardViewModel(
            requestPopularMoviesUseCase,
            requestNowPlayingMoviesUseCase,
            requestTopRatedMoviesUseCase,
            requestUpcomingMoviesUseCase,
            checkMovieIsFavoriteUseCase,
            getFavoritesMoviesUseCase
        )
    }

    @Test
    fun `Check request popular movies call if category is Popular`() = runTest {
        vm.onUiReady(Category.POPULAR)
        runCurrent()

        verify(requestPopularMoviesUseCase).invoke()
    }

    @Test
    fun `Popular movies are requested when category is Popular`() = runTest {
        whenever(requestPopularMoviesUseCase()).thenReturn(movies.right())
        whenever(checkMovieIsFavoriteUseCase(any())).thenReturn(false)

        vm.onUiReady(Category.POPULAR)
        runCurrent()
        val moviesUi = movies.map { it.toUiModel(false) }

        assertEquals(
            BillboardViewModel.UiState(loading = false, movies = moviesUi, error = null),
            vm.billboardState.value
        )
    }

    @Test
    fun `Check request now playing movies call if category is Now Playing`() = runTest {
        vm.onUiReady(Category.NOW_PLAYING)
        runCurrent()

        verify(requestNowPlayingMoviesUseCase).invoke()
    }

    @Test
    fun `Now Playing movies are requested when category is Now Playing`() = runTest {
        whenever(requestNowPlayingMoviesUseCase()).thenReturn(movies.right())
        whenever(checkMovieIsFavoriteUseCase(any())).thenReturn(false)

        vm.onUiReady(Category.NOW_PLAYING)
        runCurrent()
        val moviesUi = movies.map { it.toUiModel(false) }

        assertEquals(
            BillboardViewModel.UiState(loading = false, movies = moviesUi, error = null),
            vm.billboardState.value
        )
    }

    @Test
    fun `Check request Top rating movies call if category is Top rating`() = runTest {
        vm.onUiReady(Category.TOP)
        runCurrent()

        verify(requestTopRatedMoviesUseCase).invoke()
    }

    @Test
    fun `Top rating movies are requested when category is Now Playing`() = runTest {
        whenever(requestTopRatedMoviesUseCase()).thenReturn(movies.right())
        whenever(checkMovieIsFavoriteUseCase(any())).thenReturn(false)

        vm.onUiReady(Category.TOP)
        runCurrent()
        val moviesUi = movies.map { it.toUiModel(false) }

        assertEquals(
            BillboardViewModel.UiState(loading = false, movies = moviesUi, error = null),
            vm.billboardState.value
        )
    }

    @Test
    fun `Check request Upcoming movies call if category is Upcoming`() = runTest {
        vm.onUiReady(Category.UPCOMING)
        runCurrent()

        verify(requestUpcomingMoviesUseCase).invoke()
    }

    @Test
    fun `Upcoming movies are requested when category is Upcoming`() = runTest {
        whenever(requestUpcomingMoviesUseCase()).thenReturn(movies.right())
        whenever(checkMovieIsFavoriteUseCase(any())).thenReturn(false)

        vm.onUiReady(Category.UPCOMING)
        runCurrent()
        val moviesUi = movies.map { it.toUiModel(false) }

        assertEquals(
            BillboardViewModel.UiState(loading = false, movies = moviesUi, error = null),
            vm.billboardState.value
        )
    }

    @Test
    fun `Get favorite movies from database when category is favorite`() = runTest {
        vm.onUiReady(Category.FAVORITES)
        runCurrent()

        verify(getFavoritesMoviesUseCase).invoke()
    }

    @Test
    fun `Get movies from database when category is favorite`() = runTest {
        whenever(getFavoritesMoviesUseCase()).thenReturn(movies.right())

        vm.onUiReady(Category.FAVORITES)
        runCurrent()
        val moviesUi = movies.map { it.toUiModel(true) }

        assertEquals(
            BillboardViewModel.UiState(loading = false, movies = moviesUi, error = null),
            vm.billboardState.value
        )
    }

    @Test
    fun `Progress is shown when screen starts`() =
        runTest {
            vm.showLoading()

            val results = mutableListOf<BillboardViewModel.UiState?>()
            val observer = Observer<BillboardViewModel.UiState?> {
                results.add(it)
            }
            val job = launch {
                vm.billboardState.observeForever(observer)
            }
            runCurrent()
            job.cancel()

            assertTrue(
                results.find {
                    it?.loading == true && it.movies == null && it.error == null
                }?.let {
                    true
                } ?: false
            )
        }

}