package com.angelvictor.movies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.usecases.DatabaseEmtpyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val databaseEmtpyUseCase: DatabaseEmtpyUseCase
) : ViewModel() {

    private val _categoriesList = MutableLiveData<List<Category>>()
    val categoriesList: LiveData<List<Category>>
        get() = _categoriesList

    fun getCategories() {
        viewModelScope.launch {
            _categoriesList.postValue(
                if (databaseEmtpyUseCase()) {
                    Category.values().toList().filter { it != Category.FAVORITES }
                } else {
                    Category.values().toList()
                }
            )
        }
    }

}