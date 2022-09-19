package com.gnova.popularmovies_2022.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gnova.domain.models.Movie
import com.gnova.domain.repositories.MovieRepository
import com.gnova.popularmovies_2022.ui.RxImmediateSchedulerRule
import com.gnova.popularmovies_2022.ui.home.HomeViewModel
import junit.framework.Assert.assertEquals
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class MovieCleanerKtTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    private lateinit var movieCleaner: MovieCleaner

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieCleaner = MovieCleaner()
    }

    @Test
    fun `GIVEN getTopRatedMovies returns list of Movies WHEN onViewLoaded THEN viewState is Presenting`() {
       //WHEN
        val result = movieCleaner.removeBrokenMovies(mockCleanListEven)

        //THEN
        assertEquals(
            mockCleanListEven.dropLast(3),
            result
        )
    }

//    @Test
//    fun `GIVEN getTopRatedMovies returns list of Movies WHEN onViewLoaded THEN viewState is Presenting2`() {
//        //WHEN
//        val result = movieCleaner.removeBrokenMovies(mockCleanListOdd)
//
//        //THEN
//        assertEquals(
//            mockCleanListEven.dropLast(3),
//            result
//        )
//    }

    val mockCleanListEven = listOf<Movie>(
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

    val mockCleanListOdd = listOf<Movie>(
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