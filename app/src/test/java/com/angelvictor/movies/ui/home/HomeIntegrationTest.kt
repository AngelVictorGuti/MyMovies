package com.angelvictor.movies.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.angelvictor.movies.data.database.MovieT
import com.angelvictor.movies.data.remote.RemoteMovie
import com.angelvictor.movies.testrules.CoroutinesTestRule
import com.angelvictor.movies.ui.buildDatabaseMovies
import com.angelvictor.movies.ui.buildRemoteMovies
import com.angelvictor.movies.ui.buildRepositoryWith
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.usecases.DatabaseEmtpyUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class HomeIntegrationTest {


    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `Show favorites category if the database is not empty`() = runTest {
        val remoteData = buildRemoteMovies(4, 5, 6)
        val localData = buildDatabaseMovies(4, 5, 6)
        val vm = buildViewModelWith(
            localData = localData,
            remoteData = remoteData
        )

        vm.getCategories()
        runCurrent()

        Assert.assertEquals(
            HomeViewModel.UiState(categories = Category.values().toList()),
            vm.homeUiState.value
        )
    }

    @Test
    fun `Hide favorites category if the database is empty`() = runTest {
        val remoteData = buildRemoteMovies(4, 5, 6)
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )

        vm.getCategories()
        runCurrent()

        Assert.assertEquals(
            HomeViewModel.UiState(
                categories = Category.values().toList().filter { it != Category.FAVORITES }),
            vm.homeUiState.value
        )
    }


    private fun buildViewModelWith(
        localData: List<MovieT> = emptyList(),
        remoteData: List<RemoteMovie> = emptyList()
    ): HomeViewModel {
        val moviesRepository = buildRepositoryWith(localData, remoteData)
        val databaseEmtpyUseCase = DatabaseEmtpyUseCase(moviesRepository)
        return HomeViewModel(databaseEmtpyUseCase)
    }

}