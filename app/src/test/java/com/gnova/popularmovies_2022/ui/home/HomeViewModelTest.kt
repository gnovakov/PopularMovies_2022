package com.gnova.popularmovies_2022.ui.home

import io.reactivex.Single
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gnova.popularmovies_2022.ui.RxImmediateSchedulerRule
import com.gnova.domain.repositories.MovieRepository
import com.gnova.domain.models.Movie
import com.gnova.popularmovies_2022.R
import com.gnova.popularmovies_2022.common.MovieCleaner
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.junit.ClassRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    private lateinit var homeViewModel: HomeViewModel

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var movieCleaner: MovieCleaner

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        homeViewModel = HomeViewModel(movieRepository, movieCleaner)
    }

    @Test
    fun `GIVEN getTopRatedMovies returns list of Movies WHEN onViewLoaded THEN viewState is Presenting`() {
        //GIVEN
        val cleanedMovies = movies.dropLast(3)

        whenever(movieRepository.getTopRatedMovies("popularity.desc")).thenReturn(Single.just(movies))
        whenever(movieCleaner.removeBrokenMovies(movies)).thenReturn(cleanedMovies)

        //WHEN
        homeViewModel.onViewLoaded()

        //THEN
        assertEquals(HomeViewState.Presenting(cleanedMovies), homeViewModel.viewState.value)
    }

    @Test
    fun `GIVEN getTopRatedMovies fails to returns WHEN onViewLoaded THEN viewState is Error`() {
        //GIVEN
        whenever(movieRepository.getTopRatedMovies("popularity.desc")).thenReturn(Single.error(Throwable()))

        //WHEN
        homeViewModel.onViewLoaded()

        //THEN
        assertEquals(HomeViewState.Error(R.string.network_error), homeViewModel.viewState.value)
    }

    private val movies = listOf<Movie>(
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

}