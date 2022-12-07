package com.angelvictor.movies.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieT ORDER BY title")
    suspend fun getAll(): List<MovieT>

    @Query("SELECT COUNT(id) FROM MovieT")
    suspend fun movieCount(): Int

    @Query("SELECT favorite FROM MovieT WHERE id = :id")
    suspend fun checkMovieIsFavorite(id: Int): Boolean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieT)

    @Delete
    suspend fun deleteMovie(movie: MovieT)
}