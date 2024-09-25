package com.lemieux.movielist.data.remote

import com.lemieux.movielist.BuildConfig
import com.lemieux.movielist.data.remote.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
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
}