package com.lemieux.data.repository

import android.net.http.HttpException
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import app.cash.turbine.test
import com.lemieux.data.local.model.MovieEntity
import com.lemieux.data.remote.MovieApi
import com.lemieux.data.remote.model.detail.DetailDto
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.IOException
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class MovieRepositoryImplTest {

    private lateinit var repository: MovieRepositoryImpl
    private lateinit var api: MovieApi
    private lateinit var pager: Pager<Int, MovieEntity>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        api = mock(MovieApi::class.java)
        pager = mock(Pager::class.java) as Pager<Int, MovieEntity>
        repository = MovieRepositoryImpl(api, pager)

        // Set test dispatcher for coroutines
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()  // reset dispatcher
    }


    @Test
    fun `getMovieDetails should return movie details`() = runBlocking {
        // Given a mocked API that returns a movie detail
        val detailDto = DetailDto(
            adult = false,
            backdropPath = "backdropPath",
            title = "Movie Title",
        )
        `when`(api.getMovieDetail(1)).thenReturn(detailDto)

        // When fetching movie details
        val result = repository.getMovieDetails(1)

        // Then the correct details should be returned
        assertEquals("Movie Title", result.title)
    }


    @Test
    fun `getPopularMovies Empty list`() = runTest {
        // Verify that the function returns an empty list when the data source is empty.
        // Given an empty PagingData
        val emptyPagingData = PagingData.empty<MovieEntity>()
        `when`(pager.flow).thenReturn(flowOf(emptyPagingData))


        // When getting popular movies
        repository.getPopularMovies().test {
            val pagingData = awaitItem()
            assertEquals(emptyPagingData.toList(), pagingData.toList())
            awaitComplete()
        }
    }

    @Test
    fun `getPopularMovies Network error`() = runTest {
        `when`(pager.flow).thenReturn(flow { throw IOException("Network Error") })

        repository.getPopularMovies().test {
            withContext(Dispatchers.Default.limitedParallelism(1)) {
                val error = awaitError()
                assertTrue(error is IOException)
                assertEquals("Network Error", error.message)
            }
        }
    }

    @Test
    fun `getPopularMovies Paging functionality`() = runTest {
        val firstPage = PagingData.from(listOf(MovieEntity(1, 123, "path1", posterPath = "fefre")))
        val secondPage =
            PagingData.from(listOf(MovieEntity(2, 1234, "path2", posterPath = "asdasd")))
        `when`(pager.flow).thenReturn(flowOf(firstPage, secondPage))

        repository.getPopularMovies().test {
            withContext(Dispatchers.Default.limitedParallelism(1)) {
                assertEquals(firstPage.toList(), awaitItem().toList())
                assertEquals(secondPage.toList(), awaitItem().toList())
                awaitComplete()
            }
        }
    }


    suspend fun <T : Any> PagingData<T>.toList(): List<T> {
        val items = mutableListOf<T>()
        val pager = flowOf(this)
        pager.collectLatest { pagingData ->
            pagingData.map { items.add(it) }
        }
        return items
    }

    @Test
    fun `getPopularMovies Data mapping`() = runTest {
        val movieEntity = MovieEntity(1, 321321, "path", "rewrw")
        val pagingData = PagingData.from(listOf(movieEntity))
        `when`(pager.flow).thenReturn(flowOf(pagingData))

        repository.getPopularMovies().test {
            val paging = awaitItem()
            assertEquals(pagingData.toList(), paging.toList())
            awaitComplete()
        }
    }

    @Test
    fun `getPopularMovies Cancellation`() = runTest {
        val job = launch { repository.getPopularMovies().collect {} }
        job.cancel()
        assertTrue(job.isCancelled)
    }

// getMovieDetails tests

    @Test
    fun `getMovieDetails Valid movie ID`() = runTest {
        val detail = DetailDto(id = 1, title = "Valid Movie", posterPath = "path")
        `when`(api.getMovieDetail(1)).thenReturn(detail)

        val actual = repository.getMovieDetails(1)
        assertEquals(detail.id, actual.id)
        assertEquals(detail.title, actual.title)
        assertEquals(detail.posterPath, actual.posterPath)
    }

    @Test
    fun `getMovieDetails Data mapping`() = runTest {
        val detailDto = DetailDto(
            id = 1, title = "Roror", posterPath = "path"
        )
        `when`(api.getMovieDetail(1)).thenReturn(detailDto)

        val detail = repository.getMovieDetails(1)
        assertEquals(detailDto.id, detail.id)
        assertEquals(detailDto.title, detail.title)
        assertEquals(detailDto.posterPath, detail.posterPath)
    }

    @Test
    fun `getMovieDetails Exception handling`() = runTest {
        `when`(api.getMovieDetail(1)).thenThrow(RuntimeException("Unexpected Error"))

        try {
            repository.getMovieDetails(1)
            fail("Expected a RuntimeException")
        } catch (e: RuntimeException) {
            assertEquals("Unexpected Error", e.message)
        }
    }

    @Test
    fun `getMovieDetails Large data set`() = runTest {
        val largeDetail =
            DetailDto(adult = true, backdropPath = "L".repeat(10000), homepage = "p".repeat(10000))
        `when`(api.getMovieDetail(1)).thenReturn(largeDetail)

        val result = repository.getMovieDetails(1)
        assertEquals(largeDetail.backdropPath, result.backdropPath)
        assertEquals(largeDetail.homepage, result.homepage)
    }

}


