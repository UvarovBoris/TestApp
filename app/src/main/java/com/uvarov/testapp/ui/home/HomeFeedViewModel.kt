package com.uvarov.testapp.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeFeedUiState())
    val uiState: StateFlow<HomeFeedUiState> = _uiState.asStateFlow()

    fun incrementCounter() {
        _uiState.update { it.copy(counter = it.counter + 1) }
    }
}
