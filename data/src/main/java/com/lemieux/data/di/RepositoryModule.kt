package com.lemieux.data.di

import com.lemieux.data.repository.MovieRepositoryImpl
import com.lemieux.domain.repository.MovieRepository
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
