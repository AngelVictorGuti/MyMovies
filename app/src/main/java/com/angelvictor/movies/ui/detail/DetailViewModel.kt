package com.angelvictor.movies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.ui.common.MovieUi
import com.angelvictor.movies.ui.common.fromUiModel
import com.angelvictor.movies.usecases.ChangeMovieFavoriteUseCase
import com.angelvictor.movies.usecases.DatabaseEmtpyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val changeMovieFavoriteUseCase: ChangeMovieFavoriteUseCase,
    private val databaseEmtpyUseCase: DatabaseEmtpyUseCase
) : ViewModel() {

    private val _movie = MutableLiveData<MovieUi>()
    val movie: LiveData<MovieUi>
        get() = _movie

    private val _error = MutableLiveData<Error?>()
    val error: LiveData<Error?>
        get() = _error

    private var emptyDatabase = false

    private val minimumAverage = 5.0

    fun showAverage(average: Double) = average > minimumAverage

    fun onUiReady(movie: MovieUi){
        viewModelScope.launch {
            _movie.postValue(movie)
        }
    }

    fun favoriteOnClick(movie: MovieUi){
        viewModelScope.launch {
            val newMovie: MovieUi = movie.copy(favorite = !movie.favorite)
            val error = changeMovieFavoriteUseCase(newMovie.fromUiModel())
            _movie.postValue(newMovie)
            _error.postValue(error)
            emptyDatabase = databaseEmtpyUseCase()
        }
    }

    fun checkDatabaseIsEmpty(): Boolean = emptyDatabase


}