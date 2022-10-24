package com.angelvictor.movies.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angelvictor.movies.R
import com.angelvictor.movies.databinding.FragmentHomeBinding
import com.angelvictor.movies.ui.common.Category
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private val adapter: CategoriesAdapter = CategoriesAdapter(emptyList()) { category ->
        openMovieList(category)
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.rvCategories.adapter = adapter
        observeCategories()

    }

    private fun observeCategories() {
        viewModel.categoriesList.observe(viewLifecycleOwner) { categories ->
            adapter.updateCategories(categories)
        }
    }

    private fun openMovieList(category: Category){
        findNavController().navigate(HomeFragmentDirections.actionHomeToBillboard(category))
    }

}