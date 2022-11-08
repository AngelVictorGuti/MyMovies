package com.angelvictor.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.angelvictor.movies.R
import com.angelvictor.movies.databinding.FragmentHomeBinding
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.ui.common.PermissionState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var homeState: HomeState

    private val adapter: CategoriesAdapter = CategoriesAdapter(emptyList()) { category ->
        viewModel.onClickCategory(
            category = category,
            checkPermissions = {
                homeState.checkLocationPermission { permissionState ->
                    afterPermissionResult(permissionState, category)
                }
            },
            openNextScreen = { homeState.openMovieList(category) }
        )
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeState = buildHomeState()
        binding = FragmentHomeBinding.bind(view)
        binding.rvCategories.adapter = adapter
        observeCategories()
        viewModel.getCategories()
    }

    private fun observeCategories() {
        viewModel.homeUiState.observe(viewLifecycleOwner) { homeState ->
            homeState.categories?.let {
                adapter.updateCategories(it)
            }
        }
    }

    private fun afterPermissionResult(permissionState: PermissionState, category: Category) {
        viewModel.permissionsResult(
            permissionState = permissionState,
            openNextScreen = { homeState.openMovieList(category) },
            openLocationDeniedDialog = { homeState.showAlertDeniedLocation(category) }
        )
    }

}