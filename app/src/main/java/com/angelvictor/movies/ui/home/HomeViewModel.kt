package com.angelvictor.movies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelvictor.movies.ui.common.Category
import com.angelvictor.movies.ui.common.PermissionState
import com.angelvictor.movies.usecases.DatabaseEmtpyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val databaseEmtpyUseCase: DatabaseEmtpyUseCase
) : ViewModel() {

    private val _homeUiState = MutableLiveData<UiState>()
    val homeUiState: LiveData<UiState>
        get() = _homeUiState

    fun getCategories() {
        viewModelScope.launch {
            _homeUiState.postValue(
                UiState(
                    if (databaseEmtpyUseCase()) {
                        Category.values().toList().filter { it != Category.FAVORITES }
                    } else {
                        Category.values().toList()
                    }
                )
            )
        }
    }

    fun onClickCategory(
        category: Category,
        checkPermissions: () -> Unit,
        openNextScreen: () -> Unit
    ) {
        if (category != Category.FAVORITES) {
            checkPermissions()
        } else {
            openNextScreen()
        }
    }

    fun permissionsResult(
        permissionState: PermissionState,
        openNextScreen: () -> Unit,
        openLocationDeniedDialog: () -> Unit
    ) {
        when (permissionState) {
            PermissionState.GRANTED, PermissionState.DENIED -> {
                openNextScreen()
            }
            PermissionState.EXPLAINED -> {
                openLocationDeniedDialog()
            }
        }
    }

    data class UiState(
        val categories: List<Category>? = null
    )

}