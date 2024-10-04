package com.lemieux.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.lemieux.data.local.model.MovieEntity
import com.lemieux.data.remote.MovieApi
import com.lemieux.data.remote.model.MovieDto
import com.lemieux.data.remote.model.detail.toDetail
import com.lemieux.domain.model.Detail
import com.lemieux.domain.model.Movie
import com.lemieux.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val pager: Pager<Int, MovieEntity>,
) : MovieRepository {

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return pager.flow.map { pagingData ->
            pagingData.map { it.toMovie() }
        }.flowOn(Dispatchers.Default)
    }

    override suspend fun getMovieDetails(movieId: Int)
            : Detail = withContext(Dispatchers.IO) {
            api.getMovieDetail(movieId)
        }.toDetail()

}


fun MovieDto.toMovie() = Movie(
    id = this.id,
    title = this.title,
    posterPath = this.posterPath
)

fun MovieEntity.toMovie() = Movie(
    id = this.id,
    title = this.title,
    posterPath = this.posterPath
)