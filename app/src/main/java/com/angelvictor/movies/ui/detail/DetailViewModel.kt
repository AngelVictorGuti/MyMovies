package com.angelvictor.movies.ui.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(

) : ViewModel() {

    private val minimumAverage = 5.0

    fun showAverage(average: Double) = average > minimumAverage

}