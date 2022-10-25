package com.angelvictor.movies.ui.billboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelvictor.movies.domain.Movie
import com.angelvictor.movies.ui.common.CategoryType
import com.angelvictor.movies.usecases.RequestNowPlayingMoviesUseCase
import com.angelvictor.movies.usecases.RequestPopularMoviesUseCase
import com.angelvictor.movies.usecases.RequestTopRatedMoviesUseCase
import com.angelvictor.movies.usecases.RequestUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillboardViewModel @Inject constructor(
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val requestNowPlayingMoviesUseCase: RequestNowPlayingMoviesUseCase,
    private val requestTopRatedMoviesUseCase: RequestTopRatedMoviesUseCase,
    private val requestUpcomingMoviesUseCase: RequestUpcomingMoviesUseCase
) : ViewModel() {

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    fun onUiReady(categoryType: CategoryType) {
        viewModelScope.launch {
            val movieList: List<Movie> = when (categoryType) {
                CategoryType.NOW_PLAYING -> requestNowPlayingMoviesUseCase()
                CategoryType.POPULAR -> requestPopularMoviesUseCase()
                CategoryType.TOP -> requestTopRatedMoviesUseCase()
                CategoryType.UPCOMING -> requestUpcomingMoviesUseCase()
            }

            _moviesList.postValue(movieList)
        }

    }

}