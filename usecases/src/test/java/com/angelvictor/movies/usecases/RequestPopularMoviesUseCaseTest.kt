package com.angelvictor.movies.usecases

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.domain.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class RequestPopularMoviesUseCaseTest{

    @Test
    fun `Invoke popular movies`(): Unit = runBlocking {
        val movies: Either<Error, List<Movie>> = listOf(sampleMovie.copy(id = 1)).right()
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(
            moviesRepository = mock { onBlocking { requestPopularMovies() } doReturn (movies) })

        val result = requestPopularMoviesUseCase()

        assertEquals(movies, result)
    }

    @Test
    fun `Return error after invoke popular movies`(): Unit = runBlocking {
        val error: Either<Error, List<Movie>> = Error.Connectivity.left()
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(
            moviesRepository = mock { onBlocking { requestPopularMovies() } doReturn (error) })

        val result = requestPopularMoviesUseCase()

        assertEquals(error, result)
    }

}