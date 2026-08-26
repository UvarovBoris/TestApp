package com.uvarov.testapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvarov.testapp.ui.theme.TestAppTheme

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        state = uiState,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome, ${state.userName}!",
            style = MaterialTheme.typography.headlineMedium
        )
        if (state.items.isEmpty()) {
            Text(
                text = "No items yet",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            state.items.forEach { item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TestAppTheme {
        HomeScreen(
            state = HomeUiState(
                userName = "Android",
                items = listOf("Item 1", "Item 2")
            )
        )
    }
}
