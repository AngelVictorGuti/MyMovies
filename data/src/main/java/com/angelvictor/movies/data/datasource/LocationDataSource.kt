package com.angelvictor.movies.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}