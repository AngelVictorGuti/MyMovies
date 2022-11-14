package com.angelvictor.movies.usecases

import com.angelvictor.movies.data.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DatabaseEmtpyUseCaseTest {


    @Test
    fun `Check if database is empty`(): Unit = runBlocking {
        val databaseEmtpyUseCase = DatabaseEmtpyUseCase(mock {
            onBlocking { databaseIsEmpty() } doReturn true
        })

        val result = databaseEmtpyUseCase()

        Assert.assertEquals(true, result)

    }

    @Test
    fun `Check if database is not empty`(): Unit = runBlocking {
        val databaseEmtpyUseCase = DatabaseEmtpyUseCase(mock {
            onBlocking { databaseIsEmpty() } doReturn false
        })

        val result = databaseEmtpyUseCase()

        Assert.assertEquals(false, result)

    }

}