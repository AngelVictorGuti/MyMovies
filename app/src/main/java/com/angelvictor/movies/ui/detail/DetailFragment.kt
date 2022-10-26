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
import com.angelvictor.movies.ui.common.loadUrl
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
        initUi()
        setupToolbar()
    }

    private fun initUi() {
        binding.apply {
            ivMovie.loadUrl(args.movie.backdropPath)
            ivAdultOnly.isVisible = args.movie.adult
            tvTittleMovie.text = args.movie.originalTitle
            starRating.numStars = ceil(args.movie.voteAverage).toInt()
            starRating.rating = args.movie.voteAverage.toFloat()
            starRating.isVisible = viewModel.showAverage(args.movie.voteAverage)
            tvVoteCount.isVisible = viewModel.showAverage(args.movie.voteAverage)
            tvVoteCount.text = args.movie.voteCount.toString()
            tvDateMovie.text = args.movie.releaseDate
            tvDescriptionMovie.text = args.movie.overview
            ivFavorite.setImageResource(if (args.movie.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
            ivFavorite.setOnClickListener {
                viewModel.favoriteOnClick(args.movie)
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        binding.toolbar.title = args.movie.title
    }


}