package com.uvarov.testapp.ui.catdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.uvarov.testapp.data.model.Breed
import com.uvarov.testapp.data.model.Cat
import com.uvarov.testapp.ui.theme.TestAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatDetailScreen(
    cat: Cat,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
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
                .padding(horizontal = 16.dp)
        ) {
            val shape = RoundedCornerShape(12.dp)
            SubcomposeAsyncImage(
                model = cat.imageUrl,
                contentDescription = cat.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(shape)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, shape),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                }
            )

            cat.breeds.forEach { breed ->
                Spacer(modifier = Modifier.height(16.dp))
                BreedDetailsCard(breed = breed)
            }
        }
    }
}

@Composable
private fun BreedDetailsCard(
    breed: Breed,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = breed.name,
                style = MaterialTheme.typography.headlineSmall
            )
            DetailRow(label = "Origin", value = breed.origin)
            DetailRow(label = "Life span", value = breed.lifeSpan)
            if (!breed.temperament.isNullOrBlank()) {
                Text(
                    text = "Temperament",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    breed.temperament.split(", ").forEach { trait ->
                        SuggestionChip(
                            onClick = {},
                            label = { Text(trait) }
                        )
                    }
                }
            }
            breed.description?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String?,
    modifier: Modifier = Modifier,
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
