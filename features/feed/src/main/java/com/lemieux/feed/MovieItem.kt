package com.lemieux.feed

import android.annotation.SuppressLint
import androidx.compose.runtime.Stable
import com.lemieux.domain.model.Movie

@Stable
data class MovieItem(
    val id: Int,
    val title: String,
    val posterPath: String?,
)

fun Movie.toMovieItem() = MovieItem(
    id = id,
    title = title,
    posterPath = posterPath,
)