package com.angelvictor.movies.ui.common

import com.angelvictor.movies.domain.Movie

fun mockListMovie(id: Int): Movie = Movie(
    id,
    "Title",
    "Overview",
    "01/01/2025",
    "https://image.tmdb.org/t/p/w185//tn3GWm0Erehkpur8PUuYWxGpul5.jpg",
    "https://image.tmdb.org/t/p/w780//sobIeWp1a3saZTBkoRTAf8sfC7J.jpg",
    "EN",
    "Title",
    5.0,
    5.1,
    false
)