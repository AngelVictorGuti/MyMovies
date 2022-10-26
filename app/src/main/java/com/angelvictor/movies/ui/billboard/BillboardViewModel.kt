package com.angelvictor.movies.ui.billboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _moviesList = MutableLiveData<List<MovieUi>>()
    val moviesList: LiveData<List<MovieUi>>
        get() = _moviesList

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader

    fun onUiReady(category: Category) {
        viewModelScope.launch {
            _loader.postValue(true)
            val movieList: List<MovieUi> = when (category) {
                Category.NOW_PLAYING -> requestNowPlayingMoviesUseCase()
                Category.POPULAR -> requestPopularMoviesUseCase()
                Category.TOP -> requestTopRatedMoviesUseCase()
                Category.UPCOMING -> requestUpcomingMoviesUseCase()
                Category.FAVORITES -> getFavoritesMoviesUseCase()
            }.map { movie ->
                movie.toUiModel(checkMovieFavorite(movie.id, category))
            }

            _moviesList.postValue(movieList)
            _loader.postValue(false)
        }

    }

    private suspend fun checkMovieFavorite(id: Int, category: Category): Boolean {
        return if (category != Category.FAVORITES) {
            checkMovieIsFavoriteUseCase(id)
        } else {
            true
        }
    }

}