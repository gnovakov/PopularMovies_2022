package com.gnova.popularmovies_2022.ui.home

import io.reactivex.Single
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gnova.popularmovies_2022.ui.RxImmediateSchedulerRule
import com.gnova.domain.repositories.MovieRepository
import com.gnova.domain.models.Movie
import com.gnova.popularmovies_2022.R
import com.nhaarman.mockitokotlin2.mock
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

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        homeViewModel = HomeViewModel(movieRepository)
    }

    @Test
    fun `GIVEN getTopRatedMovies returns list of Movies WHEN onViewLoaded THEN viewState is Presenting`() {
        //GIVEN
        val mockMovies = mock<List<Movie>>()
        whenever(movieRepository.getTopRatedMovies("popularity.desc")).thenReturn(Single.just(mockMovies))

        //WHEN
        homeViewModel.onViewLoaded()

        //THEN
        assertEquals(HomeViewState.Presenting(mockMovies), homeViewModel.viewState.value)
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

}