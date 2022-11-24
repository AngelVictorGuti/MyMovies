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
import com.angelvictor.movies.ui.common.toResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillboardFragment : Fragment(R.layout.fragment_billboard) {

    private val viewModel: BillboardViewModel by viewModels()

    private lateinit var billboardState: BillboardState

    private val adapter: MoviesAdapter = MoviesAdapter(emptyList()) { movie ->
        billboardState.openDetail(movie)
    }

    private lateinit var binding: FragmentBillboardBinding
    private val args: BillboardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        billboardState = buildBillboardState()
        binding = FragmentBillboardBinding.bind(view)
        initUi()
        setupToolbar()
        observeState()
        viewModel.onUiReady(args.category)
    }

    private fun initUi() {
        binding.rvBillboard.adapter = adapter
    }

    private fun observeState() {
        viewModel.billboardState.observe(viewLifecycleOwner) { state ->
            updateUi(state)
        }
    }

    private fun setupToolbar() {
        binding.toolbarBillboard.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.toolbarBillboard.setTitle(args.category.toResource())
    }

    private fun updateUi(state: BillboardViewModel.UiState){
        binding.frameLoader.loading.isVisible = state.loading == true
        binding.rvBillboard.isVisible = state.movies != null
        state.movies?.let {
            adapter.updateMovies(it)
        }
        billboardState.showError(
            view = binding.coordinatorBillboard,
            error = state.error,
            onRetryAction = { viewModel.onUiReady(args.category) }
        )
    }

}