package com.gnova.domain.repositories

import com.gnova.domain.models.Movie
import com.gnova.domain.models.Trailer
import io.reactivex.Single

interface MovieRepository {

    fun getTopRatedMovies(sortBy: String): Single<List<Movie>>

    fun getTrailers(id: Int): Single<List<Trailer>>

}