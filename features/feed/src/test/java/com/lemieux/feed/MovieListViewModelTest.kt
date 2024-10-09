package com.lemieux.feed

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.SavedStateHandle
import com.lemieux.domain.repository.MovieRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {


    // Mocks for repository and savedStateHandle
    private lateinit var repository: MovieRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: MovieListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = mock(MovieRepository::class.java)
        savedStateHandle = mock(SavedStateHandle::class.java)
        viewModel = MovieListViewModel(repository, savedStateHandle)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `restoreLazyListState returns LazyListIndexes with default values`() {
        // Mock the savedStateHandle to return null values, ensuring defaults are used
        `when`(savedStateHandle.get<Int>(FIRST_VISIBLE_ITEM_INDEX)).thenReturn(null)
        `when`(savedStateHandle.get<Int>(FIRST_VISIBLE_ITEM_SCROLL_OFFSET)).thenReturn(null)

        val result = viewModel.restoreLazyListState()

        assertEquals(0, result.firstVisibleItemIndex)
        assertEquals(0, result.firstVisibleItemScrollOffset)
    }

    @Test
    fun `restoreLazyListState returns LazyListIndexes with saved values`() {
        `when`(savedStateHandle.get<Int>(FIRST_VISIBLE_ITEM_INDEX)).thenReturn(2)
        `when`(savedStateHandle.get<Int>(FIRST_VISIBLE_ITEM_SCROLL_OFFSET)).thenReturn(30)

        val result = viewModel.restoreLazyListState()

        assertEquals(2, result.firstVisibleItemIndex)
        assertEquals(30, result.firstVisibleItemScrollOffset)
    }

    @Test
    fun `rememberLazyListState saves LazyListState values to savedStateHandle`() {
        val lazyListState = LazyListState(
            firstVisibleItemIndex = 3,
            firstVisibleItemScrollOffset = 50
        )

        viewModel.rememberLazyListState(lazyListState)

        verify(savedStateHandle)[FIRST_VISIBLE_ITEM_INDEX] = 3
        verify(savedStateHandle)[FIRST_VISIBLE_ITEM_SCROLL_OFFSET] = 50
    }
}