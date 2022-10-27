package com.angelvictor.movies.ui.home

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.angelvictor.movies.R
import com.angelvictor.movies.databinding.FragmentHomeBinding
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.ui.common.Dialog
import com.angelvictor.movies.ui.common.RequestPermission
import com.angelvictor.movies.ui.common.openSettingsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private val adapter: CategoriesAdapter = CategoriesAdapter(emptyList()) { category ->
        viewModel.onClickCategory(
            category = category,
            checkPermissions = { checkLocationPermission(category) },
            openNextScreen = { openMovieList(category) }
        )
    }

    private lateinit var binding: FragmentHomeBinding

    private val locationPermissionRequester: RequestPermission = RequestPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.rvCategories.adapter = adapter
        observeCategories()
        viewModel.getCategories()
    }

    private fun observeCategories() {
        viewModel.categoriesList.observe(viewLifecycleOwner) { categories ->
            adapter.updateCategories(categories)
        }
    }

    private fun openMovieList(category: Category) {
        findNavController().navigate(HomeFragmentDirections.actionHomeToBillboard(category))
    }

    private fun checkLocationPermission(category: Category) {
        viewLifecycleOwner.lifecycleScope.launch {
            val permissionState = locationPermissionRequester.request()
            viewModel.permissionsResult(
                permissionState = permissionState,
                openNextScreen = { openMovieList(category) },
                openLocationDeniedDialog = { showAlertLocation(category) }
            )
        }
    }

    private fun showAlertLocation(category: Category) {
        Dialog.showAlertLocation(
            context = requireActivity(),
            actionPositive = { requireActivity().openSettingsScreen() },
            actionNegative = { openMovieList(category) }
        )
    }

}