package com.angelvictor.movies.ui.common

import android.os.Parcelable
import androidx.annotation.StringRes
import com.angelvictor.movies.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @StringRes val title: Int,
    val type: CategoryType
): Parcelable

enum class CategoryType {
    NOW_PLAYING, POPULAR, TOP, UPCOMING
}

fun getCategories() = listOf(
    Category(title = R.string.category_now_playing, type = CategoryType.NOW_PLAYING),
    Category(title = R.string.category_popular, type = CategoryType.POPULAR),
    Category(title = R.string.category_top, type = CategoryType.TOP),
    Category(title = R.string.category_upcoming, type = CategoryType.UPCOMING)
)
