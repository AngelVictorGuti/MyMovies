package com.angelvictor.movies.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.angelvictor.movies.R
import com.angelvictor.movies.databinding.FragmentDetailBinding
import com.angelvictor.movies.ui.common.CustomSnackbar
import com.angelvictor.movies.ui.common.loadUrl
import com.angelvictor.movies.ui.common.errorToString
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.ceil

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        observeMovie()
        observeError()
        setupToolbar()
        viewModel.onUiReady(args.movie)
    }

    private fun observeMovie() {
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            binding.apply {
                ivMovie.loadUrl(movie.backdropPath)
                ivAdultOnly.isVisible = movie.adult
                tvTittleMovie.text = movie.originalTitle
                starRating.numStars = ceil(movie.voteAverage).toInt()
                starRating.rating = movie.voteAverage.toFloat()
                starRating.isVisible = viewModel.showAverage(movie.voteAverage)
                tvVoteCount.isVisible = viewModel.showAverage(movie.voteAverage)
                tvVoteCount.text = movie.voteCount.toString()
                tvDateMovie.text = movie.releaseDate
                tvDescriptionMovie.text = movie.overview
                ivFavorite.setImageResource(if (movie.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
                ivFavorite.setOnClickListener {
                    viewModel.favoriteOnClick(movie)
                }
            }
        }
    }

    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                CustomSnackbar.showError(
                    binding.coordinatorDetail,
                    error.errorToString(requireContext(), it)
                ) { viewModel.movie.value?.let { movie -> viewModel.favoriteOnClick(movie) } }
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            if (viewModel.checkDatabaseIsEmpty()) {
                findNavController().navigate(DetailFragmentDirections.actionDetailToHome())
            } else {
                findNavController().popBackStack()
            }
        }
        binding.toolbar.title = args.movie.title
    }


}