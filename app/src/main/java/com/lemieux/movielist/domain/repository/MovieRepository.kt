package com.lemieux.movielist.domain.repository

import androidx.paging.PagingData
import com.lemieux.movielist.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
}