package com.lemieux.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.lemieux.data.local.MovieEntity
import com.lemieux.data.local.TmbdDatabase
import com.lemieux.data.remote.MovieApi
import com.lemieux.data.remote.MoviePagingSource
import com.lemieux.data.remote.MovieRemoteMediator
import com.lemieux.data.remote.model.MovieDto
import com.lemieux.data.remote.model.detail.toDetail
import com.lemieux.domain.model.Detail
import com.lemieux.domain.model.Movie
import com.lemieux.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val db: TmbdDatabase,
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MovieRemoteMediator(api, db),
            pagingSourceFactory = { db.movieDao.repoEntityPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toMovie() }
        }.flowOn(Dispatchers.Default)
    }

    override fun getMovieDetails(movieId: Int): Flow<Detail> = flow {
        val detail = withContext(Dispatchers.IO) {
            api.getMovieDetail(movieId)
        }
        emit(detail.toDetail())
    }.flowOn(Dispatchers.Default)
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