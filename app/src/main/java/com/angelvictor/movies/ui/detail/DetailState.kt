package com.angelvictor.movies.ui.detail

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.ui.common.CustomSnackbar
import com.angelvictor.movies.ui.common.MovieUi
import com.angelvictor.movies.ui.common.errorToString

fun Fragment.buildDetailState(
    context: Context = requireContext(),
    navController: NavController = findNavController()
) = DetailState(context, navController)

class DetailState(
    private val context: Context,
    private val navController: NavController
) {

    fun openHome(){
        navController.navigate(DetailFragmentDirections.actionDetailToHome())
    }

    fun showError(view: View, error: Error?, onRetryAction: () -> Unit) {
        error?.let {
            CustomSnackbar.showError(
                view,
                error.errorToString(context)
            ) { onRetryAction() }
        }
    }

}