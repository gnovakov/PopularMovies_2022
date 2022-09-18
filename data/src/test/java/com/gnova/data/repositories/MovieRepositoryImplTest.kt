package com.gnova.data.repositories

import com.gnova.data.api.MovieApi
import com.gnova.data.api.response.MovieResponse
import com.gnova.data.api.response.TrailersResponse
import com.gnova.data.mappers.MovieDTOMapper
import com.gnova.data.mappers.TrailerDTOMapper
import com.gnova.domain.models.Movie
import com.gnova.domain.models.Trailer
import com.gnova.domain.repositories.MovieRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Test


internal class MovieRepositoryImplTest {

    private val  mockMovieApi: MovieApi = mockk()
    private val  mockMovieMapper: MovieDTOMapper = mockk()
    private val  mockTrailerMapper: TrailerDTOMapper = mockk()

    private val mockMovieResponse = mockk<MovieResponse>()
    private val mockTrailerResponse = mockk<TrailersResponse>()

    private val mockDomainMovie = listOf<Movie>()
    private val mockDomainTrailer = listOf<Trailer>()

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieRepository = MovieRepositoryImpl(mockMovieApi, mockMovieMapper, mockTrailerMapper)
        every { mockMovieMapper.mapToDomainList(mockMovieResponse.results) } returns mockDomainMovie
        every { mockTrailerMapper.mapToDomainList(mockTrailerResponse.results) } returns mockDomainTrailer
    }

    @Test
    fun `GIVEN getTopRatedMovies is successful WHEN mapping is performed THEN no error is thrown and mapping works`() {
        //GIVEN
        every { mockMovieApi.getTopRatedMovies(any(),any(),any(),any(),any(),any()) } returns Single.just(mockMovieResponse)

        //WHEN
        val expectedResponseDomain = mockMovieMapper.mapToDomainList(mockMovieResponse.results)

        //THEN
        movieRepository.getTopRatedMovies("popularity.desc").test()
            .assertValue { it == expectedResponseDomain }
    }

    @Test
    fun `GIVEN getTrailers is successful WHEN mapping is performed THEN no error is thrown and mapping works`() {
        //GIVEN
        every { mockMovieApi.getTrailers(any(),any(),any()) } returns Single.just(mockTrailerResponse)

        //WHEN
        val expectedResponseDomain = mockTrailerMapper.mapToDomainList(mockTrailerResponse.results)

        //THEN
        movieRepository.getTrailers(1).test()
            .assertValue { it == expectedResponseDomain }
    }

}