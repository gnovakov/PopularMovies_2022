package com.gnova.popularmovies_2022.ui.home

import androidx.annotation.StringRes
import com.gnova.domain.models.Movie

sealed class HomeViewState {

    data class Presenting( val results: List<Movie>) : HomeViewState()

    data class Error(@StringRes val message: Int) : HomeViewState()

    object Loading : HomeViewState()
}