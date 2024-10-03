package com.lemieux.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int
)