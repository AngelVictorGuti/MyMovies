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

class RequestTopRatedMoviesUseCaseTest{

    @Test
    fun `Invoke top rated movies`(): Unit = runBlocking {
        val movies: Either<Error, List<Movie>> = listOf(sampleMovie.copy(id = 1)).right()
        val requestTopRatedMoviesUseCase = RequestTopRatedMoviesUseCase(
            moviesRepository = mock { onBlocking { requestTopRatedMovies() } doReturn (movies) })

        val result = requestTopRatedMoviesUseCase()

        assertEquals(movies, result)
    }

    @Test
    fun `Return error after invoke top rated movies`(): Unit = runBlocking {
        val error: Either<Error, List<Movie>> = Error.Server(402).left()
        val requestTopRatedMoviesUseCase = RequestTopRatedMoviesUseCase(
            moviesRepository = mock { onBlocking { requestTopRatedMovies() } doReturn (error) })

        val result = requestTopRatedMoviesUseCase()

        assertEquals(error, result)
    }

}