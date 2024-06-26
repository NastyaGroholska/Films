package com.ahrokholska.films.presentation.screens.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    HomeScreenContent(
        films = viewModel.films.collectAsState().value,
        onItemClick = onItemClick,
        animatedVisibilityScope = animatedVisibilityScope
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreenContent(
    films: Result<List<Film>>?,
    onItemClick: (Int) -> Unit = {},
    animatedVisibilityScope: AnimatedVisibilityScope? = null
) {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when {
                films == null -> CircularProgressIndicator()
                films.isFailure -> Text(
                    text = films.exceptionOrNull()?.message
                        ?: stringResource(R.string.something_went_wrong)
                )

                else -> LazyColumn {
                    val items = films.getOrThrow()
                    itemsIndexed(items) { index, item ->
                        Row(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .clickable { onItemClick(item.id) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var modifier = Modifier.weight(1f)
                            animatedVisibilityScope?.let {
                                modifier = modifier.sharedElement(
                                    state = rememberSharedContentState(key = item.id),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                            }
                            AsyncImage(
                                modifier = modifier,
                                model = item.posterPath,
                                placeholder = rememberVectorPainter(Icons.Default.Close),
                                contentDescription = null,
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = item.title, style = MaterialTheme.typography.titleLarge)
                                Text(text = "${item.voteAverage}")
                                Text(
                                    text = item.overview,
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }
                        if (index != items.size - 1) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun HomeScreenPreview() {
    SharedTransitionScope {
        HomeScreenContent(
            films = Result.success(
                listOf(
                    Film(
                        id = 1,
                        posterPath = "",
                        title = "Hello",
                        voteAverage = 8.99f,
                        overview = "Hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"
                    )
                )
            )
        )
    }
}