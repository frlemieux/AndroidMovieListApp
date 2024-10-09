package com.lemieux.feed

import com.lemieux.domain.model.Movie
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class MovieItemKtTest {

    // toMovieItem

    @Test
    fun `Verify successful mapping`() {
        // Given a valid `Movie` object
        val movie = Movie(id = 1, title = "Sample Movie", posterPath = "/sample.jpg")

        // When converting to `MovieItem`
        val movieItem = movie.toMovieItem()

        // Then the fields should map correctly
        assertEquals(1, movieItem.id)
        assertEquals("Sample Movie", movieItem.title)
        assertEquals("/sample.jpg", movieItem.posterPath)
    }

    @Test
    fun `Handle null posterPath`() {
        // Given a `Movie` object with a null posterPath
        val movie = Movie(id = 2, title = "Movie without poster", posterPath = null)

        // When converting to `MovieItem`
        val movieItem = movie.toMovieItem()

        // Then the `posterPath` should remain null
        assertEquals(2, movieItem.id)
        assertEquals("Movie without poster", movieItem.title)
        assertNull(movieItem.posterPath)
    }

    @Test
    fun `Empty title handling`() {
        // Given a `Movie` object with an empty title
        val movie = Movie(id = 3, title = "", posterPath = "/empty_title.jpg")

        // When converting to `MovieItem`
        val movieItem = movie.toMovieItem()

        // Then the `title` should be an empty string
        assertEquals(3, movieItem.id)
        assertEquals("", movieItem.title)  // Empty title
        assertEquals("/empty_title.jpg", movieItem.posterPath)
    }

    @Test
    fun `Large title handling`() {
        // Given a `Movie` object with a very long title
        val longTitle = "A".repeat(1000) // Very long title string
        val movie = Movie(id = 4, title = longTitle, posterPath = "/long_title.jpg")

        // When converting to `MovieItem`
        val movieItem = movie.toMovieItem()

        // Then the `title` should not be truncated
        assertEquals(4, movieItem.id)
        assertEquals(longTitle, movieItem.title)  // Long title should be preserved
        assertEquals("/long_title.jpg", movieItem.posterPath)
    }

    @Test
    fun `ID edge cases`() {
        // Given a `Movie` object with a negative ID
        val negativeIdMovie = Movie(id = -1, title = "Negative ID", posterPath = "/negative_id.jpg")
        val zeroIdMovie = Movie(id = 0, title = "Zero ID", posterPath = "/zero_id.jpg")
        val largeIdMovie = Movie(id = Int.MAX_VALUE, title = "Large ID", posterPath = "/large_id.jpg")

        // When converting to `MovieItem`
        val negativeIdItem = negativeIdMovie.toMovieItem()
        val zeroIdItem = zeroIdMovie.toMovieItem()
        val largeIdItem = largeIdMovie.toMovieItem()

        // Then the `id` should match the input
        assertEquals(-1, negativeIdItem.id)
        assertEquals(0, zeroIdItem.id)
        assertEquals(Int.MAX_VALUE, largeIdItem.id)
    }

    @Test
    fun `Data class immutability`() {
        // Given a `Movie` object
        val movie = Movie(id = 5, title = "Immutable Test", posterPath = "/immutable.jpg")

        // When converting to `MovieItem`
        val movieItem = movie.toMovieItem()

        // Then the original `Movie` object should remain unchanged
        assertEquals(5, movie.id) // Ensuring original id is unchanged
        assertEquals("Immutable Test", movie.title) // Ensuring original title is unchanged
        assertEquals("/immutable.jpg", movie.posterPath) // Ensuring original posterPath is unchanged
    }

    @Test
    fun `Performance with large objects`() {
        // Given a `Movie` object with very large data
        val largeTitle = "A".repeat(10_000) // Extremely long title string
        val largePosterPath = "/poster_path".repeat(1_000) // Extremely long posterPath
        val movie = Movie(id = 6, title = largeTitle, posterPath = largePosterPath)

        // When converting to `MovieItem`
        val movieItem = movie.toMovieItem()

        // Then the conversion should still map fields correctly
        assertEquals(6, movieItem.id)
        assertEquals(largeTitle, movieItem.title)
        assertEquals(largePosterPath, movieItem.posterPath)
    }

}