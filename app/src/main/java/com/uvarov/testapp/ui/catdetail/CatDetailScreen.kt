package com.uvarov.testapp.ui.catdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.uvarov.testapp.data.model.Breed
import com.uvarov.testapp.data.model.Cat
import com.uvarov.testapp.ui.theme.TestAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatDetailScreen(
    cat: Cat,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = cat.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = cat.imageUrl,
                contentDescription = cat.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            if (cat.breeds.isEmpty()) {
                Text(
                    text = "No breed info available",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                cat.breeds.forEach { breed ->
                    BreedDetails(breed = breed)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun BreedDetails(
    breed: Breed,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = breed.name,
            style = MaterialTheme.typography.headlineSmall
        )
        DetailRow(label = "Origin", value = breed.origin)
        DetailRow(label = "Temperament", value = breed.temperament)
        DetailRow(label = "Life span", value = breed.lifeSpan)
        breed.description?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String?,
    modifier: Modifier = Modifier
) {
    if (value != null) {
        Row(modifier = modifier.fillMaxWidth()) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.width(120.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatDetailScreenPreview() {
    TestAppTheme {
        CatDetailScreen(
            cat = Cat(
                id = "aph",
                name = "Whiskers",
                imageUrl = "https://cdn2.thecatapi.com/images/aph.jpg",
                breeds = listOf(
                    Breed(
                        id = "abys",
                        name = "Abyssinian",
                        temperament = "Active, Energetic, Independent",
                        origin = "Egypt",
                        lifeSpan = "14 - 15 years",
                        description = "The Abyssinian is a lithe, finely boned cat with a striking ticked coat."
                    )
                )
            ),
            onBack = {}
        )
    }
}
