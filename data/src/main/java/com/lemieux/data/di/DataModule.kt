package com.lemieux.data.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lemieux.data.local.TmbdDatabase
import com.lemieux.data.remote.MovieApi
import com.lemieux.data.remote.MovieRemoteMediator
import com.lemieux.data.repository.MovieRepositoryImpl
import com.lemieux.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideGithubRetrofit(): MovieApi {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.MILLISECONDS)
            .readTimeout(1200, TimeUnit.MILLISECONDS)
            .addInterceptor(logger).build()

        return Retrofit
            .Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)
    : TmbdDatabase = TmbdDatabase.getInstance(context)

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideRepository(api: MovieApi, db: TmbdDatabase)
    : MovieRepository = MovieRepositoryImpl(api, Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MovieRemoteMediator(api, db),
            pagingSourceFactory = { db.movieDao.pagingSource() }
        ))
}
