package com.lemieux.domain.repository

import android.annotation.SuppressLint
import androidx.paging.PagingData
import com.lemieux.domain.model.Detail
import com.lemieux.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    @SuppressLint("VisibleForTests")
    fun getPopularMovies(): Flow<PagingData<Movie>>

    suspend fun getMovieDetails(movieId: Int): Detail
}