package com.uvarov.testapp.ui.favorites

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    fun incrementFavorites() {
        _uiState.update { it.copy(favoriteCount = it.favoriteCount + 1) }
    }

    fun onNoteChange(note: String) {
        _uiState.update { it.copy(note = note) }
    }
}
