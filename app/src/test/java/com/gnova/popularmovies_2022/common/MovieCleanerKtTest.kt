package com.gnova.popularmovies_2022.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gnova.domain.models.Movie
import junit.framework.Assert.assertEquals
import org.junit.*
import org.mockito.MockitoAnnotations

internal class MovieCleanerKtTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var movieCleaner: MovieCleaner

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieCleaner = MovieCleaner()
    }

    @Test
    fun `GIVEN a list of movies WHEN title, overview, poster_path is null THEN return clean movies`() {
       //WHEN
        val result = movieCleaner.removeBrokenMovies(moviesEven)

        //THEN
        assertEquals(
            moviesEven.dropLast(3),
            result
        )
    }

    @Test
    fun `GIVEN a list of movies WHEN title, overview, poster_path is null THEN return even number of clean movies `() {
        //WHEN
        val result = movieCleaner.removeBrokenMovies(moviesOdd)

        //THEN
        assertEquals(
            moviesOdd.dropLast(4),
            result
        )
    }

    private val moviesEven = listOf<Movie>(
        Movie(
            id = 1,
            vote_average = 2.2,
            title = "movie",
            release_date = "01/01/2050",
            backdrop_path = "path",
            overview = "movie",
            poster_path = "path"
        ),
        Movie(
            id = 2,
            vote_average = 2.2,
            title = "movie",
            release_date = "01/01/2050",
            backdrop_path = "path",
            overview = "movie",
            poster_path = "path"
        ),
        Movie(
            id = 2,
            vote_average = 2.2,
            title = "movie",
            release_date = "01/01/2050",
            backdrop_path = "path",
            overview = "movie",
            poster_path = ""
        ),
        Movie(
            id = 2,
            vote_average = 2.2,
            title = "",
            release_date = "01/01/2050",
            backdrop_path = "path",
            overview = "movie",
            poster_path = "path"
        ),
        Movie(
            id = 2,
            vote_average = 2.2,
            title = "movie",
            release_date = "01/01/2050",
            backdrop_path = "path",
            overview = "",
            poster_path = "path"
        )
    )

    private val moviesOdd = listOf<Movie>(
        Movie(
            id = 1,
            vote_average = 2.2,
            title = "movie",
            release_date = "01/01/2050",
            backdrop_path = "path",
            overview = "movie",
            poster_path = "path"
        ),
        Movie(
            id = 2,
            vote_average = 2.2,
            title = "movie",
            release_date = "01/01/2050",
            backdrop_path = "path",
            overview = "movie",
            poster_path = "path"
        ),
        Movie(
            id = 2,
            vote_average = 2.2,
            title = "movie",
            release_date = "01/01/2050",
            backdrop_path = "path",
            overview = "movie",
            poster_path = "path"
        ),
        Movie(
            id = 2,
            vote_average = 2.2,
            title = "movie",
            release_date = "01/01/2050",
            backdrop_path = "path",
            overview = "movie",
            poster_path = ""
        ),
        Movie(
            id = 2,
            vote_average = 2.2,
            title = "",
            release_date = "01/01/2050",
            backdrop_path = "path",
            overview = "movie",
            poster_path = "path"
        ),
        Movie(
            id = 2,
            vote_average = 2.2,
            title = "movie",
            release_date = "01/01/2050",
            backdrop_path = "path",
            overview = "",
            poster_path = "path"
        )
    )
}