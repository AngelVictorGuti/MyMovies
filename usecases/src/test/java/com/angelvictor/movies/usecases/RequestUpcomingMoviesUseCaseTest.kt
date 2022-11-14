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

class RequestUpcomingMoviesUseCaseTest{

    @Test
    fun `Invoke upcoming movies`(): Unit = runBlocking {
        val movies: Either<Error, List<Movie>> = listOf(sampleMovie.copy(id = 1)).right()
        val requestUpcomingMoviesUseCase = RequestUpcomingMoviesUseCase(
            moviesRepository = mock { onBlocking { requestUpcomingMovies() } doReturn (movies) })

        val result = requestUpcomingMoviesUseCase()

        assertEquals(movies, result)
    }

    @Test
    fun `Return error after invoke upcoming movies`(): Unit = runBlocking {
        val error: Either<Error, List<Movie>> = Error.Server(403).left()
        val requestUpcomingMoviesUseCase = RequestUpcomingMoviesUseCase(
            moviesRepository = mock { onBlocking { requestUpcomingMovies() } doReturn (error) })

        val result = requestUpcomingMoviesUseCase()

        assertEquals(error, result)
    }


}