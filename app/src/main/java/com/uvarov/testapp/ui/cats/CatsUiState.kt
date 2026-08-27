package com.uvarov.testapp.ui.cats

import com.uvarov.testapp.data.model.Cat

data class CatsUiState(
    val cats: List<Cat> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true
) {
    companion object {
        val Initial = CatsUiState()
    }
}
