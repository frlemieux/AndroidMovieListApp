package com.lemieux.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.AsyncImage

fun WindowSizeClass.isCompact() =
    windowWidthSizeClass == WindowWidthSizeClass.COMPACT ||
            windowHeightSizeClass == WindowHeightSizeClass.COMPACT

@Composable
fun MovieDetailsScreen(
    id: Int,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    DisposableEffect(Unit) {
        onDispose {
            viewModel.cleanUp()
        }
    }
    LaunchedEffect(id) {
        viewModel.getMovieDetails(id)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        when (state) {
            is MovieDetailsUiState.Success -> {
                MovieDetailsView((state as MovieDetailsUiState.Success).movie)
            }

            is MovieDetailsUiState.Error -> {
                Text("Error: ${(state as MovieDetailsUiState.Error).exception.localizedMessage}")
            }

            MovieDetailsUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }

}

@Composable
fun MovieDetailsView(movie: MovieDetails) {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val windowSize = with(LocalDensity.current) {
        currentWindowSize().toSize().toDpSize()
    }
    if (adaptiveInfo.windowSizeClass.isCompact()) {
        MovieDetailsViewCompact(
            movie = movie,
            content = {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        )
    } else {
        Row {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .weight(0.5f)
                    .padding(top = 16.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            MovieDetailsViewCompact(
                movie,
                modifier = Modifier
                    .weight(0.5f)
            )
        }
    }
}


@Composable
fun MovieDetailsViewCompact(
    movie: MovieDetails,
    content: @Composable ColumnScope.() -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        content()
        // Title & Tagline
        Text(
            text = movie.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = movie.tagline,
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Genres
        LazyRow {
            items(movie.genres) { genre ->
                Text(
                    text = genre.name,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(Color(0xFFD32F2F), RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Overview
        Text(
            text = "Overview",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = movie.overview,
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Budget & Revenue
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text("Budget", fontWeight = FontWeight.Bold)
                Text("$${movie.budget}")
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Revenue", fontWeight = FontWeight.Bold)
                Text("$${movie.revenue}")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Runtime & Release Date
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text("Runtime", fontWeight = FontWeight.Bold)
                Text("${movie.runtime} min")
            }
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text("Release Date", fontWeight = FontWeight.Bold)
                Text(movie.releaseDate)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Production Companies
        Text(
            text = "Production Companies",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(movie.productionCompanies) { company ->
                Card(
                    modifier = Modifier
                        .weight(0.4f)
                        .height(IntrinsicSize.Max)
                        .padding(all = 16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(company.name, fontWeight = FontWeight.Bold)
                        company.logoPath?.let {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w200/${it}",
                                contentDescription = null,
                                modifier = Modifier
                                    .aspectRatio(0.75f)
                                    .height(80.dp),
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun MovieDetailsPreview() {
    MovieDetailsView(
        MovieDetails(
            title = "Deadpool & Wolverine",
            tagline = "Come together.",
            posterPath = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
            genres = listOf(
                Genre(28, "Action"),
                Genre(35, "Comedy"),
                Genre(878, "Science Fiction")
            ),
            overview = "A listless Wade Wilson toils away...",
            budget = 200000000,
            revenue = 1321225740,
            runtime = 128,
            releaseDate = "2024-07-24",
            productionCompanies = listOf(
                ProductionCompany(420, "Marvel Studios", "/hUzeosd33nzE5MCNsZxCGEKTXaQ.png"),
                ProductionCompany(104228, "Maximum Effort", "/hx0C1XcSxGgat8N62GpxoJGTkCk.png")
            )
        )
    )
}
