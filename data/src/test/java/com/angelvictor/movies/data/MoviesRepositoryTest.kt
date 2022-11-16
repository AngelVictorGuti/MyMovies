package com.angelvictor.movies.data

import arrow.core.right
import com.angelvictor.movies.data.datasource.MovieLocalDataSource
import com.angelvictor.movies.data.datasource.MovieRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest{

    @Mock
    lateinit var remoteDataSource: MovieRemoteDataSource

    @Mock
    lateinit var movieLocalDataSource: MovieLocalDataSource

    @Mock
    lateinit var locationRepository: LocationRepository

    private lateinit var moviesRepository: MoviesRepository

    private val localMovies = listOf(sampleMovie.copy(1)).right()

    @Before
    fun setUp() {
        moviesRepository = MoviesRepository(remoteDataSource, movieLocalDataSource, locationRepository)
    }

    @Test
    fun `Get favorite movies from local data source`(): Unit = runBlocking {
        whenever(movieLocalDataSource.getMovies()).thenReturn(localMovies)

        val result = moviesRepository.getFavoriteMovies()

        assertEquals(localMovies, result)
    }

    @Test
    fun `Get popular movies`(): Unit = runBlocking {
        val remoteMovies = listOf(sampleMovie.copy(2)).right()
        whenever(locationRepository.findRegion()).thenReturn(LocationRepository.DEFAULT_REGION)
        whenever(remoteDataSource.getPopularMovies(any())).thenReturn(remoteMovies)

        val result = moviesRepository.requestPopularMovies()

        assertEquals(remoteMovies, result)
    }

    @Test
    fun `Get top rated movies`(): Unit = runBlocking {
        val remoteMovies = listOf(sampleMovie.copy(3)).right()
        whenever(locationRepository.findRegion()).thenReturn(LocationRepository.DEFAULT_REGION)
        whenever(remoteDataSource.getTopRatedMovies(any())).thenReturn(remoteMovies)

        val result = moviesRepository.requestTopRatedMovies()

        assertEquals(remoteMovies, result)
    }

    @Test
    fun `Get now playing movies`(): Unit = runBlocking {
        val remoteMovies = listOf(sampleMovie.copy(4)).right()
        whenever(locationRepository.findRegion()).thenReturn(LocationRepository.DEFAULT_REGION)
        whenever(remoteDataSource.getNowPlayingMovies(any())).thenReturn(remoteMovies)

        val result = moviesRepository.requestNowPlayingMovies()

        assertEquals(remoteMovies, result)
    }

    @Test
    fun `Get upcoming movies`(): Unit = runBlocking {
        val remoteMovies = listOf(sampleMovie.copy(5)).right()
        whenever(locationRepository.findRegion()).thenReturn(LocationRepository.DEFAULT_REGION)
        whenever(remoteDataSource.getUpcomingMovies(any())).thenReturn(remoteMovies)

        val result = moviesRepository.requestUpcomingMovies()

        assertEquals(remoteMovies, result)
    }

    @Test
    fun `Check if database is empty`(): Unit = runBlocking {
        whenever(movieLocalDataSource.isEmpty()).thenReturn(false)

        val result = moviesRepository.databaseIsEmpty()

        assertEquals(false, result)
    }

    @Test
    fun `Check if movie is favorite`(): Unit = runBlocking {
        whenever(movieLocalDataSource.checkMovieIsFavorite(sampleMovie.id)).thenReturn(false)

        val result = moviesRepository.checkMovieIsFavorite(sampleMovie.id)

        assertEquals(false, result)
    }

    @Test
    fun `Save movie in database after marks as favorite`(): Unit = runBlocking {
        val movie = sampleMovie.copy(favorite = true)

        moviesRepository.changeFavorite(movie)

        verify(movieLocalDataSource).saveMovie(movie)
    }

    @Test
    fun `Delete movie from database after marks as unfavorite`(): Unit = runBlocking {
        val movie = sampleMovie.copy(favorite = false)

        moviesRepository.changeFavorite(movie)

        verify(movieLocalDataSource).deleteMovie(movie)
    }

}