package com.lemieux.domain.model

import androidx.annotation.VisibleForTesting

@VisibleForTesting
data class Movie (
    val id: Int,
    val title: String,
    val posterPath: String?,
)