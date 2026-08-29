package com.uvarov.testapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uvarov.testapp.core.ui.theme.TestAppTheme

@Composable
fun HomeRoute(
    onOpenCats: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        state = uiState,
        onIncrement = viewModel::incrementCounter,
        onOpenCats = onOpenCats,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
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
            state = HomeUiState(counter = 5),
            onIncrement = {},
            onOpenCats = {}
        )
    }
}
