package com.angelvictor.movies.ui.common

import com.angelvictor.movies.R

enum class Category {
    NOW_PLAYING, POPULAR, TOP, UPCOMING, FAVORITES
}

fun Category.toStringFromResource(): Int {
    return when (this) {
        Category.NOW_PLAYING -> R.string.category_now_playing
        Category.POPULAR -> R.string.category_popular
        Category.TOP -> R.string.category_top
        Category.UPCOMING -> R.string.category_upcoming
        Category.FAVORITES -> R.string.category_favorites
    }
}