package com.angelvictor.movies.ui.billboard

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.angelvictor.movies.R
import com.angelvictor.movies.databinding.FragmentBillboardBinding
import com.angelvictor.movies.ui.common.CustomSnackbar
import com.angelvictor.movies.ui.common.MovieUi
import com.angelvictor.movies.ui.common.toResource
import com.angelvictor.movies.ui.common.errorToString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillboardFragment : Fragment(R.layout.fragment_billboard) {

    private val viewModel: BillboardViewModel by viewModels()

    private val adapter: MoviesAdapter = MoviesAdapter(emptyList()) { movie ->
        openDetail(movie)
    }

    private lateinit var binding: FragmentBillboardBinding
    private val args: BillboardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBillboardBinding.bind(view)
        initUi()
        setupToolbar()
        observeMovies()
        observeLoader()
        observeError()
        viewModel.onUiReady(args.category)
    }

    private fun initUi() {
        binding.rvBillboard.adapter = adapter
    }

    private fun observeMovies() {
        viewModel.moviesList.observe(viewLifecycleOwner) {
            adapter.updatemovies(it)
        }
    }

    private fun observeLoader() {
        viewModel.loader.observe(viewLifecycleOwner) {
            binding.frameLoader.loading.isVisible = it
        }
    }

    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                CustomSnackbar.showError(
                    binding.coordinatorBillboard,
                    error.errorToString(requireContext(), it)
                ) { viewModel.onUiReady(args.category) }
            }
        }
    }

    private fun openDetail(movie: MovieUi){
        findNavController().navigate(BillboardFragmentDirections.actionBillboardDestToDetailFragment(movie))
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.toolbar.setTitle(args.category.toResource())
    }

}