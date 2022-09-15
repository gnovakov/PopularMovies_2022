package com.gnova.domain.mappers

interface DomainMapper <E, D> {

    fun mapToDomain(entity: E) : D

    fun mapToEntity(domainModel: D) : E
}