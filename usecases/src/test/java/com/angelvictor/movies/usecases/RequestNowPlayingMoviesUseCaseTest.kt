package com.angelvictor.movies.usecases

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.domain.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class RequestNowPlayingMoviesUseCaseTest{


    @Test
    fun `Invoke now playing movies`(): Unit = runBlocking {
        val movies: Either<Error, List<Movie>> = listOf(sampleMovie.copy(id = 1)).right()
        val requestNowPlayingMoviesUseCase = RequestNowPlayingMoviesUseCase(
            moviesRepository = mock { onBlocking { requestNowPlayingMovies() } doReturn (movies) })

        val result = requestNowPlayingMoviesUseCase()

        assertEquals(movies, result)
    }

    @Test
    fun `Return error after invoke now playing movies`(): Unit = runBlocking {
        val error: Either<Error, List<Movie>> = Error.Server(401).left()
        val requestNowPlayingMoviesUseCase = RequestNowPlayingMoviesUseCase(
            moviesRepository = mock { onBlocking { requestNowPlayingMovies() } doReturn (error) })

        val result = requestNowPlayingMoviesUseCase()

        assertEquals(error, result)
    }


}