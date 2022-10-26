package com.angelvictor.movies.ui.detail

import androidx.lifecycle.ViewModel
import com.angelvictor.movies.ui.common.MovieUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(

) : ViewModel() {

    private val minimumAverage = 5.0

    fun showAverage(average: Double) = average > minimumAverage

    fun favoriteOnClick(movie: MovieUi){

    }

}