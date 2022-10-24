package com.angelvictor.movies.ui.billboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angelvictor.movies.domain.Movie
import com.angelvictor.movies.ui.common.mockListMovie

class BillboardViewModel : ViewModel(

) {

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    init {
        _moviesList.postValue((1..10).map{ mockListMovie(it) })
    }

}