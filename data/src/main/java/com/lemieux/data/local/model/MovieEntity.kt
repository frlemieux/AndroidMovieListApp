package com.lemieux.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val index: Int = 0,
    val id: Int,
    val title: String,
    val posterPath: String?,
)