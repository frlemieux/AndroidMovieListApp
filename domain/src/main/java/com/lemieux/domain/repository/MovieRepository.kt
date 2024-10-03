package com.lemieux.domain.repository

import androidx.paging.PagingData
import com.lemieux.domain.model.Detail
import com.lemieux.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun getMovieDetails(movieId: Int): Flow<Detail>
}