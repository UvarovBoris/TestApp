package com.uvarov.testapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvarov.testapp.domain.usecase.GetCatsUseCase
import com.uvarov.testapp.domain.usecase.RefreshCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCats: GetCatsUseCase,
    private val refreshCatsUseCase: RefreshCatsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState.Initial)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCats()
    }

    fun loadCats() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val cats = getCats()
            _uiState.update { it.copy(cats = cats, isLoading = false) }
        }
    }

    fun refreshCats() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            val cats = refreshCatsUseCase()
            _uiState.update { it.copy(cats = cats, isRefreshing = false) }
        }
    }
}
