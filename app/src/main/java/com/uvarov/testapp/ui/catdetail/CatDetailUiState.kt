package com.uvarov.testapp.ui.catdetail

import com.uvarov.testapp.data.model.Cat

data class CatDetailUiState(
    val cat: Cat? = null,
    val isLoading: Boolean = true
)
