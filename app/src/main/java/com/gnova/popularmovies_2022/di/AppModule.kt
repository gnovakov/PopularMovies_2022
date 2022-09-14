package com.gnova.popularmovies_2022.di

import com.gnova.data.mappers.MovieDTOMapper
import com.gnova.data.mappers.TrailerDTOMapper
import com.gnova.data.models.MovieDTO
import com.gnova.data.models.TrailerDTO
import com.gnova.data.repositories.MovieRepositoryImpl
import com.gnova.domain.common.DomainMapper
import com.gnova.domain.models.Movie
import com.gnova.domain.models.Trailer
import com.gnova.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepo(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindMovieDTOMapper(
        movieDTOMapper: MovieDTOMapper
    ): DomainMapper<MovieDTO, Movie>

    @Binds
    @Singleton
    abstract fun bindTrailerDTOMapper(
        trailerDTOMapper: TrailerDTOMapper
    ): DomainMapper<TrailerDTO, Trailer>

}