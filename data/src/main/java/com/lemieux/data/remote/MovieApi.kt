package com.lemieux.data.remote

import com.lemieux.data.BuildConfig
import com.lemieux.data.remote.model.MovieResponse
import com.lemieux.data.remote.model.detail.DetailDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @Headers(
        "Authorization: Bearer ${BuildConfig.tmdbApiKey}",
        "accept: application/json"
    )
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MovieResponse

    @Headers(
        "Authorization: Bearer ${BuildConfig.tmdbApiKey}",
        "accept: application/json"
    )
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        ): DetailDto

}