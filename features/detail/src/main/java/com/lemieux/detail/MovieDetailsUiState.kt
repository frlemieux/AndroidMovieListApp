package com.lemieux.detail

sealed class MovieDetailsUiState {
    data class Success(val movie: MovieDetails) : MovieDetailsUiState()
    data class Error(val exception: Throwable) : MovieDetailsUiState()
    object Loading : MovieDetailsUiState()
}