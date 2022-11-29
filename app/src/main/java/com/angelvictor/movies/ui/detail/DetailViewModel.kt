package com.angelvictor.movies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.ui.common.Category
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


    private val _detailState = MutableLiveData<UiState>()
    val detailState: LiveData<UiState>
        get() = _detailState

    private val minimumAverage = 5.0

    fun showAverage(average: Double) = average > minimumAverage

    fun onUiReady(movie: MovieUi) {
        viewModelScope.launch {
            _detailState.postValue(UiState(movie = movie))
        }
    }

    fun favoriteOnClick(movie: MovieUi) {
        viewModelScope.launch {
            val newMovie: MovieUi = movie.copy(favorite = !movie.favorite)
            val error = changeMovieFavoriteUseCase(newMovie.fromUiModel())

            _detailState.postValue(UiState(movie = newMovie, error = error))
        }
    }

    fun onRetryChangeFavorite() {
        detailState.value?.movie?.let {
            favoriteOnClick(it)
        }
    }

    fun onBackPressed(
        category: Category,
        actionDatabaseIsEmpty: () -> Unit,
        actionDatabaseNotEmpty: () -> Unit
    ) {
        viewModelScope.launch {
            if (category == Category.FAVORITES && databaseEmtpyUseCase()) {
                actionDatabaseIsEmpty()
            } else {
                actionDatabaseNotEmpty()
            }
        }
    }

    data class UiState(
        val movie: MovieUi? = null,
        val error: Error? = null
    )

}