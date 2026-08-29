package com.uvarov.testapp.ui.catdetail

import com.uvarov.testapp.domain.model.Cat

data class CatDetailUiState(
    val cat: Cat? = null,
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false
)
