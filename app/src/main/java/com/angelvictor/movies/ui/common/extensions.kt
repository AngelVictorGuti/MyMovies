package com.angelvictor.movies.ui.common

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.angelvictor.movies.R
import com.bumptech.glide.Glide

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

fun Activity.openSettingsScreen() {
    this.let {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", it.packageName, null)
            startActivity(this)
        }
    }
}

fun Category.toResource(): Int {
    return when (this) {
        Category.NOW_PLAYING -> R.string.category_now_playing
        Category.POPULAR -> R.string.category_popular
        Category.TOP -> R.string.category_top
        Category.UPCOMING -> R.string.category_upcoming
        Category.FAVORITES -> R.string.category_favorites
    }
}