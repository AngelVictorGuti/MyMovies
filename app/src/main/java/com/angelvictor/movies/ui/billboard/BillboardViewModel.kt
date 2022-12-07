package com.angelvictor.movies.ui.billboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelvictor.movies.domain.Error
import com.angelvictor.movies.domain.Movie
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.ui.common.MovieUi
import com.angelvictor.movies.ui.common.toUiModel
import com.angelvictor.movies.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillboardViewModel @Inject constructor(
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val requestNowPlayingMoviesUseCase: RequestNowPlayingMoviesUseCase,
    private val requestTopRatedMoviesUseCase: RequestTopRatedMoviesUseCase,
    private val requestUpcomingMoviesUseCase: RequestUpcomingMoviesUseCase,
    private val checkMovieIsFavoriteUseCase: CheckMovieIsFavoriteUseCase,
    private val getFavoritesMoviesUseCase: GetFavoritesMoviesUseCase
) : ViewModel() {

    private val _billboardState = MutableLiveData<UiState>()
    val billboardState: LiveData<UiState>
        get() = _billboardState

    fun onUiReady(category: Category) {
        viewModelScope.launch {
            showLoading()
            val result = when (category) {
                Category.NOW_PLAYING -> requestNowPlayingMoviesUseCase.invoke()
                Category.POPULAR -> requestPopularMoviesUseCase.invoke()
                Category.TOP -> requestTopRatedMoviesUseCase.invoke()
                Category.UPCOMING -> requestUpcomingMoviesUseCase.invoke()
                Category.FAVORITES -> getFavoritesMoviesUseCase.invoke()
            }
            result.fold(
                ifLeft = { error -> onErrorCallApi(error) },
                ifRight = { list -> onSuccessCallApi(category, list) }
            )
        }

    }

    fun showLoading(){
        _billboardState.postValue(UiState(loading = true))
    }

    private suspend fun checkMovieFavorite(id: Int, category: Category): Boolean {
        return if (category != Category.FAVORITES) {
            checkMovieIsFavoriteUseCase(id)
        } else {
            true
        }
    }

    private suspend fun onSuccessCallApi(category: Category, movieList: List<Movie>) {
        val newList = movieList.map { movie ->
            movie.toUiModel(checkMovieFavorite(movie.id, category))
        }
        _billboardState.postValue(
            UiState(loading = false, movies = newList)
        )
    }

    private fun onErrorCallApi(error: Error) {
        _billboardState.postValue(
            UiState(loading = false, error = error)
        )
    }

    data class UiState(
        val loading: Boolean? = null,
        val movies: List<MovieUi>? = null,
        val error: Error? = null
    )
}