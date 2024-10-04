package com.lemieux.data.di

import com.lemieux.data.repository.MovieRepositoryImpl
import com.lemieux.domain.repository.MovieRepository



abstract class RepositoryModule {

    abstract fun bindTaskRepository(taskRepositoryImpl: MovieRepositoryImpl): MovieRepository
}
