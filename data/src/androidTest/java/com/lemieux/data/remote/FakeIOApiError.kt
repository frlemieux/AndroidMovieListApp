package com.lemieux.data.remote

import com.lemieux.data.remote.model.MovieResponse
import com.lemieux.data.remote.model.detail.DetailDto
import okio.IOException

class FakeIOApiError : MovieApi {

    override suspend fun getPopularMovies(page: Int): MovieResponse {
        throw IOException("Network Error")
    }

    override suspend fun getMovieDetail(movieId: Int): DetailDto {
        TODO("Not yet implemented")
    }

}
