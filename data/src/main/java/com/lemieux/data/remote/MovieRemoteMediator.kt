package com.lemieux.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.lemieux.data.local.MovieEntity
import com.lemieux.data.local.TmbdDatabase
import com.lemieux.data.remote.model.MovieDto
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1


@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val api: MovieApi,
    private val db: TmbdDatabase
): RemoteMediator<Int, MovieEntity>() {


    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>,
    ): MediatorResult {

        val page =
            when (loadType) {
                LoadType.REFRESH -> {
                    println("LoadType.REFRESH")
                    return MediatorResult.Success(
                        endOfPaginationReached = false,
                    )
                }

                LoadType.PREPEND -> {
                    println("LoadType.PREPEND")
                    return MediatorResult.Success(
                        endOfPaginationReached = true,
                    )
                }

                LoadType.APPEND -> {
                    println("LoadType.APPEND")
                    val lastItem =
                        db.withTransaction {
                            db.movieDao.lastIndex() ?: STARTING_PAGE_INDEX
                        }
                    if (lastItem == STARTING_PAGE_INDEX) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true,
                        )
                    } else {
                        (lastItem.div(state.config.pageSize)) + STARTING_PAGE_INDEX
                    }
                }
            }
        try {
            val apiResponse =
                api.getPopularMovies(
                    page = page,
                )
            val movies = apiResponse.results
            val endOfPaginationReached = movies.isEmpty()
            db.withTransaction {
                db.movieDao.insertAll(movies.map { it.toEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            val errorBody = exception.response()?.errorBody()?.string()
            val gson = Gson()
            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
            return MediatorResult.Error(Throwable(errorResponse.statusMessage.orEmpty()))
        }
    }

    private suspend fun resetDb() {
        db.movieDao.clearRepos()
        db.movieDao.resetPrimaryKey()
    }
}

data class ErrorResponse (
    val success: Boolean? = null,
    @SerializedName("status_code")
    val statusCode: Int? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null,
)

fun MovieDto.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
)