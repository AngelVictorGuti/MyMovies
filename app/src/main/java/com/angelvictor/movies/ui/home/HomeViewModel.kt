package com.angelvictor.movies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.ui.common.getCategories
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(

) {

    private val _categoriesList = MutableLiveData<List<Category>>()
    val categoriesList: LiveData<List<Category>>
        get() = _categoriesList

    init {
        viewModelScope.launch {
            _categoriesList.postValue(getCategories())
        }
    }

}