package com.angelvictor.movies.ui.common

import android.content.Context
import com.angelvictor.movies.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Dialog {


    fun showAlertLocation(
        context: Context,
        actionPositive: () -> Unit,
        actionNegative: () -> Unit
    ){
        MaterialAlertDialogBuilder(context)
            .setCancelable(false)
            .setTitle(R.string.location_denied_title)
            .setMessage(R.string.location_denied_message)
            .setPositiveButton(R.string.location_denied_settings) { _, _ ->
                actionPositive()
            }
            .setNegativeButton(R.string.location_denied_accept){ _, _ ->
                actionNegative()
            }
            .show()
    }
}