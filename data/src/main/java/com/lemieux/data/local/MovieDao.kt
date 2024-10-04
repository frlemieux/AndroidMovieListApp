package com.lemieux.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lemieux.data.local.model.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<MovieEntity>)

    @Query(
        "SELECT * FROM movies",
    )
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("SELECT `index` FROM `movies` ORDER BY `index` DESC LIMIT 1")
    suspend fun lastIndex(): Int?

    @Query("DELETE FROM movies")
    suspend fun clearRepos()

    @Query("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'movies'")
    suspend fun resetPrimaryKey()
}