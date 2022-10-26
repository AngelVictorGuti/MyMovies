package com.angelvictor.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieT::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}