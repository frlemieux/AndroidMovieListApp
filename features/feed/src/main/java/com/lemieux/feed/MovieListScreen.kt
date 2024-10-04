package com.lemieux.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage


@Composable
fun MovieListScreen(
    lazyListState: LazyListState,
    viewModel: MovieListViewModel = hiltViewModel(),
    onClickItem: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        val lazyListIndexes = viewModel.restoreLazyListState()
        lazyListState.scrollToItem(
            lazyListIndexes.firstVisibleItemIndex,
            lazyListIndexes.firstVisibleItemScrollOffset
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.rememberLazyListState(lazyListState)
        }
    }
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "TMDB Movies List",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (movies.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier,
                )
            } else {
                when {
                    movies.itemCount > 0 -> {
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
                        ) {
                            items(count = movies.itemCount, key = { index -> index }) { index ->
                                movies[index]?.let {
                                    MovieItem(movie = it, modifier.clickable {
                                        onClickItem(movies[index]!!.id)
                                    })
                                }
                            }
                            item {
                                if (movies.loadState.append is LoadState.Loading) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }

                    else -> {
                        ErrorHandling(
                            movies.loadState.refresh,
                            onRetry = { movies.retry() },
                        )
                        ErrorHandling(
                            movies.loadState.append,
                            onRetry = { movies.retry() },
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun ErrorHandling(loadState: LoadState, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    if (loadState is LoadState.Error) {
        EmptyContent(
            onRetry = onRetry, modifier = modifier
        )
    }
}

@Composable
private fun EmptyContent(
    onRetry: () -> Unit,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Text(
            "The service is unavailable. \nRetry later please",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
        Button(
            onClick = { onRetry() },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Retry", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
private fun MovieItem(
    movie: MovieItem,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            movie.posterPath?.let {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(0.75f)
                        .height(350.dp)
                        .weight(0.2f),
                    contentScale = ContentScale.FillWidth
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = movie.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(0.8f)
            )
        }
    }
}

@Preview
@Composable
fun PreviewMovieItem() {
    MovieItem(
        movie = MovieItem(
            id = 2345, title = "Outlaw", posterPath = "3453f3ec"
        )
    )
}
