package com.angelvictor.movies.usecases

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.domain.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetFavoritesMoviesUseCaseTest {


    @Test
    fun `Get favorites movies from database`(): Unit = runBlocking {
        val movies: Either<Error, List<Movie>> = listOf(sampleMovie.copy(id = 1)).right()
        val getFavoritesMoviesUseCase = GetFavoritesMoviesUseCase(
            moviesRepository = mock { onBlocking { getFavoriteMovies() } doReturn (movies) })

        val result = getFavoritesMoviesUseCase()

        Assert.assertEquals(movies, result)
    }

    @Test
    fun `Return error after get favorites movies from database`(): Unit = runBlocking {
        val error: Either<Error, List<Movie>> = Error.Unknown("Database Error").left()
        val getFavoritesMoviesUseCase = GetFavoritesMoviesUseCase(
            moviesRepository = mock { onBlocking { getFavoriteMovies() } doReturn (error) })

        val result = getFavoritesMoviesUseCase()

        Assert.assertEquals(error, result)
    }

}