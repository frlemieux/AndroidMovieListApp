package com.lemieux.feed

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.lemieux.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val FIRST_VISIBLE_ITEM_INDEX = "firstVisibleItemIndex"
private const val FIRST_VISIBLE_ITEM_SCROLL_OFFSET = "firstVisibleItemScrollOffset"

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    fun restoreLazyListState() : LazyListIndexes {
       return LazyListIndexes(
                firstVisibleItemIndex = savedStateHandle[FIRST_VISIBLE_ITEM_INDEX] ?: 0,
                firstVisibleItemScrollOffset = savedStateHandle[FIRST_VISIBLE_ITEM_SCROLL_OFFSET]
                    ?: 0,
            )
    }

    fun rememberLazyListState(lazyListState: LazyListState) {
        savedStateHandle[FIRST_VISIBLE_ITEM_INDEX] = lazyListState.firstVisibleItemIndex
        savedStateHandle[FIRST_VISIBLE_ITEM_SCROLL_OFFSET] =
            lazyListState.firstVisibleItemScrollOffset
    }

    val movies: Flow<PagingData<MovieItem>> = repository.getPopularMovies()
        .map { pagingData ->
            pagingData.map {
                it.toMovieItem()
            }
        }
        .flowOn(Dispatchers.Default)

}

data class LazyListIndexes(
    val firstVisibleItemIndex: Int,
    val firstVisibleItemScrollOffset: Int,
)