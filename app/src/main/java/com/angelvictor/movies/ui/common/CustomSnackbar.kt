package com.angelvictor.movies.ui.common

import android.view.View
import com.angelvictor.movies.R
import com.google.android.material.snackbar.Snackbar

object CustomSnackbar {

    fun showError(
        view: View,
        msg: String,
        actionRetry: () -> Unit
    ) {
        Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.error_dialog_retry) { actionRetry() }
            .setBackgroundTint(view.context.getColor(R.color.error))
            .setTextColor(view.context.getColor(R.color.white))
            .setActionTextColor(view.context.getColor(R.color.white))
            .show()
    }
}