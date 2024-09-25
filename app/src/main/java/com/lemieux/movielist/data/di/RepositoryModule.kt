package com.lemieux.tasks.data.di

import com.lemieux.movielist.data.repository.MovieRepositoryImpl
import com.lemieux.movielist.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent

@Module
@InstallIn(ViewComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTaskRepository(taskRepositoryImpl: MovieRepositoryImpl): MovieRepository
}
