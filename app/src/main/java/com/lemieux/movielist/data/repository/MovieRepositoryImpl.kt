package com.lemieux.movielist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.lemieux.movielist.data.remote.MovieApi
import com.lemieux.movielist.data.remote.MoviePagingSource
import com.lemieux.movielist.data.remote.model.MovieDto
import com.lemieux.movielist.domain.model.Movie
import com.lemieux.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieApi) : MovieRepository {

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviePagingSource(api) }
        ).flow.map{ pagingData ->
            pagingData.map { it.toMovie() }
        }
    }
}


fun MovieDto.toMovie() = Movie(
    id = this.id,
    title = this.title,
    posterPath = this.posterPath
)