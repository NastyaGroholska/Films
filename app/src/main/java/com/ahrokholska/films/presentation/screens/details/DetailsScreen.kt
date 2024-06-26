package com.ahrokholska.films.presentation.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ahrokholska.films.R
import com.ahrokholska.films.data.Film

@Composable
fun DetailsScreen(viewModel: DetailsScreenViewModel = hiltViewModel()) {
    DetailsScreenContent(film = viewModel.film.collectAsState().value)
}

@Composable
fun DetailsScreenContent(film: Result<Film>?) {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when {
                film == null -> CircularProgressIndicator()
                film.isFailure -> Text(
                    text = film.exceptionOrNull()?.message
                        ?: stringResource(R.string.something_went_wrong)
                )

                else ->
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val item = film.getOrThrow()
                        item {
                            Text(text = item.title, style = MaterialTheme.typography.titleLarge)
                            Text(text = "${item.voteAverage}")
                            AsyncImage(
                                modifier = Modifier.fillMaxWidth(),
                                model = item.posterPath,
                                placeholder = rememberVectorPainter(Icons.Default.Close),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = item.overview,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    DetailsScreenContent(
        film = Result.success(
            Film(
                id = 1,
                posterPath = "",
                title = "Hello",
                voteAverage = 8.99f,
                overview = "Hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"
            )
        )
    )
}