package com.lemieux.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lemieux.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _id = MutableStateFlow<Int?>(null)

    fun getMovieDetails(id: Int) {
        _id.update { id }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<MovieDetailsUiState> = _id
        .flatMapLatest { id ->
            if (id == null) {
                return@flatMapLatest MutableStateFlow(MovieDetailsUiState.Loading)
            } else {
                try {
                    val movieDetail = movieRepository.getMovieDetails(id)
                    return@flatMapLatest MutableStateFlow(
                        MovieDetailsUiState.Success(movieDetail.toMovieDetails())
                    )
                } catch (e: Exception) {
                    return@flatMapLatest MutableStateFlow(MovieDetailsUiState.Error(e))
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MovieDetailsUiState.Loading
        )


    fun cleanUp() {
        _id.update { null }
    }
}

suspend inline fun <T> suspendRunCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Result.failure(exception)
}