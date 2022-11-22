package com.angelvictor.movies.ui.billboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.angelvictor.movies.data.database.MovieT
import com.angelvictor.movies.data.database.toDomainModel
import com.angelvictor.movies.data.remote.RemoteMovie
import com.angelvictor.movies.data.remote.toDomainModel
import com.angelvictor.movies.testrules.CoroutinesTestRule
import com.angelvictor.movies.ui.buildDatabaseMovies
import com.angelvictor.movies.ui.buildRemoteMovies
import com.angelvictor.movies.ui.buildRepositoryWith
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.ui.common.toUiModel
import com.angelvictor.movies.usecases.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class BillboardIntegrationTest {


    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `Popular movies are loaded from the server when selecting the popular category`() =
        runTest {
            val remoteData = buildRemoteMovies(4, 5, 6)
            val vm = buildViewModelWith(
                localData = emptyList(),
                remoteData = remoteData
            )

            vm.onUiReady(Category.POPULAR)
            runCurrent()
            val moviesUi = remoteData.toDomainModel().map { it.toUiModel(false) }

            Assert.assertEquals(
                BillboardViewModel.UiState(loading = false, movies = moviesUi, error = null),
                vm.billboardState.value
            )
        }

    @Test
    fun `Now playing movies are loaded from the server when selecting the now playing category`() =
        runTest {
            val remoteData = buildRemoteMovies(7, 8, 9)
            val vm = buildViewModelWith(
                localData = emptyList(),
                remoteData = remoteData
            )

            vm.onUiReady(Category.NOW_PLAYING)
            runCurrent()
            val moviesUi = remoteData.toDomainModel().map { it.toUiModel(false) }

            Assert.assertEquals(
                BillboardViewModel.UiState(loading = false, movies = moviesUi, error = null),
                vm.billboardState.value
            )
        }

    @Test
    fun `Top rated movies are loaded from the server when selecting the top rated category`() =
        runTest {
            val remoteData = buildRemoteMovies(10, 11, 12)
            val vm = buildViewModelWith(
                localData = emptyList(),
                remoteData = remoteData
            )

            vm.onUiReady(Category.TOP)
            runCurrent()
            val moviesUi = remoteData.toDomainModel().map { it.toUiModel(false) }

            Assert.assertEquals(
                BillboardViewModel.UiState(loading = false, movies = moviesUi, error = null),
                vm.billboardState.value
            )
        }

    @Test
    fun `Upcoming movies are loaded from the server when selecting the upcoming category`() =
        runTest {
            val remoteData = buildRemoteMovies(13, 14, 15)
            val vm = buildViewModelWith(
                localData = emptyList(),
                remoteData = remoteData
            )

            vm.onUiReady(Category.UPCOMING)
            runCurrent()
            val moviesUi = remoteData.toDomainModel().map { it.toUiModel(false) }

            Assert.assertEquals(
                BillboardViewModel.UiState(loading = false, movies = moviesUi, error = null),
                vm.billboardState.value
            )
        }

    @Test
    fun `Movies are loaded from local when selecting the favorites category`() = runTest {
        val localData = buildDatabaseMovies(16, 17, 18)
        val vm = buildViewModelWith(
            localData = localData,
            remoteData = emptyList()
        )

        vm.onUiReady(Category.FAVORITES)
        runCurrent()
        val moviesUi = localData.toDomainModel().map { it.toUiModel(true) }

        Assert.assertEquals(
            BillboardViewModel.UiState(loading = false, movies = moviesUi, error = null),
            vm.billboardState.value
        )
    }

    private fun buildViewModelWith(
        localData: List<MovieT> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList()
    ): BillboardViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)
        val requestNowPlayingMoviesUseCase = RequestNowPlayingMoviesUseCase(moviesRepository)
        val requestTopRatedMoviesUseCase = RequestTopRatedMoviesUseCase(moviesRepository)
        val requestUpcomingMoviesUseCase = RequestUpcomingMoviesUseCase(moviesRepository)
        val checkMovieIsFavoriteUseCase = CheckMovieIsFavoriteUseCase(moviesRepository)
        val getFavoritesMoviesUseCase = GetFavoritesMoviesUseCase(moviesRepository)
        return BillboardViewModel(
            requestPopularMoviesUseCase,
            requestNowPlayingMoviesUseCase,
            requestTopRatedMoviesUseCase,
            requestUpcomingMoviesUseCase,
            checkMovieIsFavoriteUseCase,
            getFavoritesMoviesUseCase
        )
    }

}