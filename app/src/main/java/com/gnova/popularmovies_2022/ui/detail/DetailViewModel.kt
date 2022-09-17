package com.gnova.popularmovies_2022.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gnova.domain.models.Movie
import com.gnova.domain.repositories.MovieRepository
import com.gnova.popularmovies_2022.R
import io.reactivex.android.schedulers.AndroidSchedulers
import com.gnova.popularmovies_2022.ui.detail.DetailViewState.*
import com.gnova.popularmovies_2022.common.DisposingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) : DisposingViewModel() {

    fun onViewInit(movie: Movie) {
        _selectedMovie.value = movie
        getTrailers(movie.id)
    }

    // MutableLiveData that stores the selected movie
    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    // View State
    private val _viewState = MutableLiveData<DetailViewState>()
    val viewState: LiveData<DetailViewState>
        get() = _viewState

private fun getTrailers(id: Int) {
        _viewState.value = Loading
        add(
            movieRepository.getTrailers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _viewState.value = Presenting(it)
                }, {
                    _viewState.value = Error(R.string.network_error)
                })
        )
    }

}