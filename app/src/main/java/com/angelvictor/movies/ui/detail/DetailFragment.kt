package com.angelvictor.movies.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.angelvictor.movies.R
import com.angelvictor.movies.databinding.FragmentDetailBinding
import com.angelvictor.movies.ui.common.MovieUi
import com.angelvictor.movies.ui.common.loadUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.ceil

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var detailState: DetailState

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailState = buildDetailState()
        binding = FragmentDetailBinding.bind(view)
        observeState()
        setupToolbar()
        setupOnBackPressed()
        viewModel.onUiReady(args.movie)
    }

    private fun observeState() {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            updateUi(state)
        }
    }

    private fun updateUi(state: DetailViewModel.UiState){
        state.movie?.let {
            fillInfoMovie(it)
        }
        detailState.showError(
            view = binding.coordinatorDetail,
            error = state.error,
            onRetryAction = { viewModel.onRetryChangeFavorite() }
        )
    }

    private fun fillInfoMovie(movie: MovieUi) {
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

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { onBack() }
        binding.toolbar.title = args.movie.title
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBack()
                }
            })
    }

    private fun onBack() {
        viewModel.onBackPressed(
            actionDatabaseIsEmpty = { detailState.openHome() },
            actionDatabaseNotEmpty = { findNavController().popBackStack() }
        )
    }

}