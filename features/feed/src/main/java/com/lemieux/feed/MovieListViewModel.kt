package com.lemieux.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.lemieux.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    val movies: Flow<PagingData<MovieItem>> = repository.getPopularMovies()
        .flowOn(Dispatchers.IO)
        .map { pagingData ->
            pagingData.map {
                it.toMovieItem()
            }
        }
        .flowOn(Dispatchers.Default)

}