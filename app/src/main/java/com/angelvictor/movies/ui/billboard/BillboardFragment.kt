package com.angelvictor.movies.ui.billboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.angelvictor.movies.R
import com.angelvictor.movies.databinding.FragmentBillboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillboardFragment : Fragment(R.layout.fragment_billboard) {

    private val viewModel: BillboardViewModel by viewModels()

    private val adapter: MoviesAdapter = MoviesAdapter(emptyList()) {
        //TODO Go to detail
    }

    private lateinit var binding: FragmentBillboardBinding
    private val args: BillboardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBillboardBinding.bind(view)
        initUi()
        setupToolbar()
        observeMovies()
        viewModel.onUiReady(args.category.type)
    }

    private fun observeMovies() {
        viewModel.moviesList.observe(viewLifecycleOwner) {
            adapter.updatemovies(it)
        }
    }

    private fun initUi(){
        binding.rvBillboard.adapter = adapter
    }

    private fun setupToolbar(){
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.toolbar.setTitle(args.category.title)
    }

}