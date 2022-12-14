package com.gnova.popularmovies_2022.ui.detail

import androidx.annotation.StringRes
import com.gnova.domain.models.Trailer

sealed class DetailViewState {

    data class Presenting( val results: List<Trailer>) : DetailViewState()

    data class Error(@StringRes val message: Int) : DetailViewState()

    object Loading : DetailViewState()
}
