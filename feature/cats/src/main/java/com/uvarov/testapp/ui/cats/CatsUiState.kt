package com.uvarov.testapp.ui.cats

import com.uvarov.testapp.domain.model.Cat

data class CatsUiState(
    val cats: List<Cat> = emptyList(),
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = false,
    val isLoading: Boolean = false
)
