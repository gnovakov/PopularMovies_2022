package com.gnova.data.mappers

import com.gnova.data.models.TrailerDTO
import com.gnova.domain.models.Trailer
import javax.inject.Inject

class TrailerDTOMapper @Inject constructor() {

    fun mapToDomainList(entityDTOs: List<TrailerDTO>): List<Trailer> {
        return entityDTOs.map {
            mapToDomain(it) }
    }

    fun mapToEntityList(domainModels: List<Trailer>): List<TrailerDTO> {
        return domainModels.map {
            mapToEntity(it) }
    }

    private fun mapToDomain(entity: TrailerDTO): Trailer {
        return Trailer(
            id = entity.id,
            key = entity.key,
            name = entity.name
        )
    }

    private fun mapToEntity(domainModel: Trailer): TrailerDTO {
        return TrailerDTO(
            id = domainModel.id,
            key = domainModel.key,
            name = domainModel.name
        )
    }
}