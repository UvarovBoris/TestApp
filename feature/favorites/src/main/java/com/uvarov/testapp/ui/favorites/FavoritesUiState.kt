package com.uvarov.testapp.ui.favorites

import com.uvarov.testapp.domain.model.Cat

data class FavoritesUiState(
    val cats: List<Cat> = emptyList()
)
