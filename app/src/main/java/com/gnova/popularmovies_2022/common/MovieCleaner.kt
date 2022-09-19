package com.gnova.popularmovies_2022.common

import android.util.Log
import com.gnova.domain.models.Movie
import javax.inject.Inject

class MovieCleaner @Inject constructor() {

    fun removeBrokenMovies(movies: List<Movie>): List<Movie> {

        val cleanedMovies = movies.filterNot {
            it.title == "" ||
            it.poster_path == "" ||
            it.overview == ""
        }

        return if (cleanedMovies.size % 2 == 0)
            cleanedMovies
        else
            cleanedMovies.dropLast(1)
    }

}
