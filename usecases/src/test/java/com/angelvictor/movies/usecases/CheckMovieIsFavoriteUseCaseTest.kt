package com.angelvictor.movies.usecases

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class CheckMovieIsFavoriteUseCaseTest {


    @Test
    fun `Check if movie is favorite`(): Unit = runBlocking {
        val movie = sampleMovie.copy(id = 1)
        val checkMovieIsFavoriteUseCase = CheckMovieIsFavoriteUseCase(mock {
            onBlocking { checkMovieIsFavorite(movie.id) } doReturn true
        })

        val result = checkMovieIsFavoriteUseCase(movie.id)

        Assert.assertEquals(true, result)

    }

    @Test
    fun `Check if movie is not favorite`(): Unit = runBlocking {
        val movie = sampleMovie.copy(id = 1)
        val checkMovieIsFavoriteUseCase = CheckMovieIsFavoriteUseCase(mock {
            onBlocking { checkMovieIsFavorite(movie.id) } doReturn false
        })

        val result = checkMovieIsFavoriteUseCase(movie.id)

        Assert.assertEquals(false, result)

    }
}