package com.angelvictor.movies.ui.billboard

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.ui.common.CustomSnackbar
import com.angelvictor.movies.ui.common.MovieUi
import com.angelvictor.movies.ui.common.errorToString

fun Fragment.buildBillboardState(
    context: Context = requireContext(),
    navController: NavController = findNavController()
) = BillboardState(context, navController)

class BillboardState(
    private val context: Context,
    private val navController: NavController
) {

    fun openDetail(movie: MovieUi, category: Category) {
        navController.navigate(BillboardFragmentDirections.actionBillboardDestToDetailFragment(movie, category))
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