package com.uvarov.testapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uvarov.testapp.ui.favorites.FavoritesRoute
import com.uvarov.testapp.ui.profile.ProfileRoute
import com.uvarov.testapp.ui.theme.TestAppTheme

@Composable
fun HomeRoute(
    onOpenCats: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        state = uiState,
        onTabSelected = viewModel::onTabSelected,
        onOpenCats = onOpenCats,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onTabSelected: (HomeTab) -> Unit,
    onOpenCats: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val saveableStateHolder = rememberSaveableStateHolder()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                HomeTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = state.selectedTab == tab,
                        onClick = { onTabSelected(tab) },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title
                            )
                        },
                        label = { Text(text = tab.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            saveableStateHolder.SaveableStateProvider(key = state.selectedTab) {
                when (state.selectedTab) {
                    HomeTab.HOME -> HomeFeedRoute(onOpenCats = onOpenCats)
                    HomeTab.FAVORITES -> FavoritesRoute()
                    HomeTab.PROFILE -> ProfileRoute()
                }
            }
        }
    }
}

@Composable
fun HomeFeedRoute(
    onOpenCats: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeFeedViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeFeedScreen(
        state = uiState,
        onIncrement = viewModel::incrementCounter,
        onOpenCats = onOpenCats,
        modifier = modifier
    )
}

@Composable
fun HomeFeedScreen(
    state: HomeFeedUiState,
    onIncrement: () -> Unit,
    onOpenCats: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onOpenCats) {
            Text("Open cats")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onIncrement) {
            Text("Clicks on this tab: ${state.counter}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TestAppTheme {
        HomeScreen(
            state = HomeUiState(selectedTab = HomeTab.HOME),
            onTabSelected = {},
            onOpenCats = {}
        )
    }
}


