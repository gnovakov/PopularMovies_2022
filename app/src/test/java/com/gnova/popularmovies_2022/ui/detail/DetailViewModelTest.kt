package com.gnova.popularmovies_2022.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gnova.domain.models.Movie
import com.gnova.domain.models.Trailer
import com.gnova.domain.repositories.MovieRepository
import com.gnova.popularmovies_2022.R
import com.gnova.popularmovies_2022.ui.RxImmediateSchedulerRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import junit.framework.TestCase.assertEquals
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations


internal class DetailViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    private lateinit var detailViewModel: DetailViewModel

    @Mock
    lateinit var movieRepository: MovieRepository

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailViewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun `GIVEN getTrailers returns list of Trailers WHEN onViewLoaded THEN viewState is Presenting`() {
        //GIVEN
        val mockMovie = mock<Movie>()
        val mockTrailers = mock<List<Trailer>>()
        whenever(movieRepository.getTrailers(mockMovie.id)).thenReturn(Single.just(mockTrailers))

        //WHEN
        detailViewModel.onViewInit(mockMovie)

        //THEN
        assertEquals(DetailViewState.Presenting(mockTrailers), detailViewModel.viewState.value)
    }

    @Test
    fun `GIVEN getTrailers fails to return WHEN onViewLoaded THEN viewState is Error`() {
        val mockMovie = mock<Movie>()
        //GIVEN
        whenever(movieRepository.getTrailers(mockMovie.id)).thenReturn(Single.error(Throwable()))

        //WHEN
        detailViewModel.onViewInit(mockMovie)

        //THEN
        assertEquals(DetailViewState.Error(R.string.network_error), detailViewModel.viewState.value)
    }
}