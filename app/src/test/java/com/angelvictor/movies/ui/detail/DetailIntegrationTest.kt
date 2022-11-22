package com.angelvictor.movies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.angelvictor.movies.data.database.MovieT
import com.angelvictor.movies.data.remote.RemoteMovie
import com.angelvictor.movies.data.remote.toDomainModel
import com.angelvictor.movies.testrules.CoroutinesTestRule
import com.angelvictor.movies.ui.buildDatabaseMovies
import com.angelvictor.movies.ui.buildRemoteMovies
import com.angelvictor.movies.ui.buildRepositoryWith
import com.angelvictor.movies.ui.common.toUiModel
import com.angelvictor.movies.usecases.ChangeMovieFavoriteUseCase
import com.angelvictor.movies.usecases.DatabaseEmtpyUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.verify


@ExperimentalCoroutinesApi
class DetailIntegrationTest {


    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `The movie data is displayed when loading the screen`() = runTest {
        val remoteMovie = buildRemoteMovies(5)
        val vm = buildViewModelWith(
            remoteData = remoteMovie
        )
        val movieUi = remoteMovie.toDomainModel().map { it.toUiModel(false) }[0]

        vm.onUiReady(movieUi)
        runCurrent()

        Assert.assertEquals(
            DetailViewModel.UiState(movie = movieUi),
            vm.detailState.value
        )
    }

    @Test
    fun `Favorite is saved in local data source`() = runTest {
        val remoteMovie = buildRemoteMovies(6)
        val movieUi = remoteMovie.toDomainModel().map { it.toUiModel(false) }[0]
        val vm = buildViewModelWith(
            remoteData = remoteMovie
        )

        vm.favoriteOnClick(movieUi)
        runCurrent()

        Assert.assertEquals(true, vm.detailState.value?.movie?.favorite)
    }

    @Test
    fun `Favorite is deleted in local data source`() = runTest {
        val remoteMovie = buildRemoteMovies(6)
        val movieUi = remoteMovie.toDomainModel().map { it.toUiModel(true) }[0]
        val vm = buildViewModelWith(
            remoteData = remoteMovie
        )

        vm.favoriteOnClick(movieUi)
        runCurrent()

        Assert.assertEquals(false, vm.detailState.value?.movie?.favorite)
    }


    private fun buildViewModelWith(
        localData: List<MovieT> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList()
    ): DetailViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val changeMovieFavoriteUseCase = ChangeMovieFavoriteUseCase(moviesRepository)
        val databaseEmtpyUseCase = DatabaseEmtpyUseCase(moviesRepository)
        return DetailViewModel(
            changeMovieFavoriteUseCase,
            databaseEmtpyUseCase
        )
    }

}