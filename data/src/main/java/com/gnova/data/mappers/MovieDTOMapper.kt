package com.gnova.data.mappers

import com.gnova.data.models.MovieDTO
import com.gnova.domain.models.Movie
import javax.inject.Inject

class MovieDTOMapper @Inject constructor() {

    fun mapToDomainList(entityDTOs: List<MovieDTO>): List<Movie> {
        return entityDTOs.map {
            mapToDomain(it) }
    }

    fun mapToEntityList(domainModels: List<Movie>): List<MovieDTO> {
        return domainModels.map {
            mapToEntity(it) }
    }

    private fun mapToDomain(entity: MovieDTO): Movie {
        return Movie(
            id = entity.id,
            vote_average = entity.vote_average,
            title = entity.title,
            release_date = entity.release_date,
            backdrop_path = entity.backdrop_path,
            overview = entity.overview,
            poster_path = entity.poster_path
        )
    }

    private fun mapToEntity(domainModel: Movie): MovieDTO {
        return MovieDTO(
            id = domainModel.id,
            vote_average = domainModel.vote_average,
            title = domainModel.title,
            release_date = domainModel.release_date,
            backdrop_path = domainModel.backdrop_path,
            overview = domainModel.overview,
            poster_path = domainModel.poster_path
        )
    }
}