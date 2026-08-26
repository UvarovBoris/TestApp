package com.uvarov.testapp.ui.home

data class HomeUiState(
    val userName: String = "Guest",
    val items: List<String> = emptyList()
) {
    companion object {
        val Initial = HomeUiState()
    }
}
