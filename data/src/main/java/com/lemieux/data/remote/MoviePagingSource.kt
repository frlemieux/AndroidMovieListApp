package com.lemieux.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lemieux.data.remote.model.MovieDto

class MoviePagingSource(
    private val api: MovieApi
) : PagingSource<Int, MovieDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val page = params.key ?: 1
        return try {
            val response = api.getPopularMovies(page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}