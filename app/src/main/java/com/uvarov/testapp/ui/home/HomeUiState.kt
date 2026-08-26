package com.uvarov.testapp.ui.home

import com.uvarov.testapp.data.model.Cat

data class HomeUiState(
    val cats: List<Cat> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
) {
    companion object {
        val Initial = HomeUiState()
    }
}
