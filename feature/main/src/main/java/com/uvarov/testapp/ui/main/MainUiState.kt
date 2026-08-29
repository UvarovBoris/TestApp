package com.uvarov.testapp.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainTab(
    val title: String,
    val icon: ImageVector
) {
    HOME("Home", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    PROFILE("Profile", Icons.Default.Person)
}

data class MainUiState(
    val selectedTab: MainTab = MainTab.HOME
)
