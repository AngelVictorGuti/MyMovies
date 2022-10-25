package com.angelvictor.movies.ui.billboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelvictor.movies.domain.Movie
import com.angelvictor.movies.ui.common.CategoryType
import com.angelvictor.movies.usecases.RequestNowPlayingMoviesUseCase
import com.angelvictor.movies.usecases.RequestPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillboardViewModel @Inject constructor(
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val requestNowPlayingMoviesUseCase: RequestNowPlayingMoviesUseCase
): ViewModel() {

    private val _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    fun onUiReady(categoryType: CategoryType) {
        viewModelScope.launch {
            val movieList: List<Movie> = when(categoryType){
                CategoryType.LATEST -> requestPopularMoviesUseCase()
                CategoryType.NOW_PLAYING -> requestNowPlayingMoviesUseCase()
                CategoryType.POPULAR -> requestPopularMoviesUseCase()
                CategoryType.TOP -> requestPopularMoviesUseCase()
                CategoryType.UPCOMING -> requestPopularMoviesUseCase()
            }

            _moviesList.postValue(movieList)
        }

    }

}