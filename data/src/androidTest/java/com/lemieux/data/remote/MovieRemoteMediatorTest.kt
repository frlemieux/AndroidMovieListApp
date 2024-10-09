package com.lemieux.data.remote

import android.net.http.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lemieux.data.local.TmbdDatabase
import com.lemieux.data.local.model.MovieEntity
import com.lemieux.data.remote.model.MovieDto
import com.lemieux.data.remote.model.MovieResponse
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.IOException

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MovieRemoteMediatorTest {

    private lateinit var api: MovieApi
    private lateinit var apiError: MovieApi
    private lateinit var db: TmbdDatabase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        api = FakeApi()
        apiError = FakeIOApiError()
        db =  Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = TmbdDatabase::class.java
        ).build()

    }

    @Test
    fun refreshLoadReturnsSuccessWhenMoreDataIsPresent() = runTest {
        val remoteMediator = MovieRemoteMediator(api, db)
        val pagingState = PagingState<Int, MovieEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
    }

    @Test
    fun refreshLoadReturnsSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        val remoteMediator = MovieRemoteMediator(api, db)
        val pagingState = PagingState<Int, MovieEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadReturnsErrorWhenIOExceptionOccurs() = runTest {
        // Set up the apiError to throw an IOException
        val remoteMediator = MovieRemoteMediator(apiError, db)
        val pagingState = PagingState<Int, MovieEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertTrue((result as RemoteMediator.MediatorResult.Error).throwable is IOException)
    }
}