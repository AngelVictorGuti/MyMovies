package com.angelvictor.movies.ui.billboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelvictor.movies.data.toError
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

    private val _moviesList = MutableLiveData<List<MovieUi>>()
    val moviesList: LiveData<List<MovieUi>>
        get() = _moviesList

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader

    private val _error = MutableLiveData<Error?>()
    val error: LiveData<Error?>
        get() = _error

    fun onUiReady(category: Category) {
        viewModelScope.launch {
            _loader.postValue(true)
            when (category) {
                Category.NOW_PLAYING -> requestNowPlayingMoviesUseCase.invoke().fold(
                    ifLeft = { error -> onErrorCallApi(error) },
                    ifRight = { list -> onSuccessCallApi(category, list) }
                )

                Category.POPULAR ->
                    requestPopularMoviesUseCase.invoke().fold(
                        ifLeft = { error -> onErrorCallApi(error) },
                        ifRight = { list -> onSuccessCallApi(category, list) }
                    )

                Category.TOP -> requestTopRatedMoviesUseCase.invoke().fold(
                    ifLeft = { error -> onErrorCallApi(error) },
                    ifRight = { list -> onSuccessCallApi(category, list) }
                )
                Category.UPCOMING -> requestUpcomingMoviesUseCase.invoke().fold(
                    ifLeft = { error -> onErrorCallApi(error) },
                    ifRight = { list -> onSuccessCallApi(category, list) }
                )
                Category.FAVORITES -> getFavoritesMoviesUseCase.invoke().fold(
                    ifLeft = { error -> onErrorCallApi(error) },
                    ifRight = { list -> onSuccessCallApi(category, list) }
                )
            }
        }

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
        _moviesList.postValue(newList)
        _loader.postValue(false)
    }

    private fun onErrorCallApi(error: Error) {
        _error.postValue(error)
        _loader.postValue(false)
    }

}