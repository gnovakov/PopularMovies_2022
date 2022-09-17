package com.gnova.popularmovies_2022.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gnova.domain.models.Movie
import com.gnova.popularmovies_2022.ui.home.HomeViewState.*
import com.gnova.domain.repositories.MovieRepository
import com.gnova.popularmovies_2022.R
import com.gnova.popularmovies_2022.util.DisposingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository): DisposingViewModel()  {


    // View State
    private val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState>
        get() = _viewState

    // Holds the selected Movie data
    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()
    val navigateToSelectedMovie: LiveData<Movie?>
        get() = _navigateToSelectedMovie


    fun onViewLoaded() {
        getTopRatedMovies("popularity.desc")
    }


    private fun getTopRatedMovies(sortBy : String) {

        _viewState.value = Loading
        add(
            movieRepository.getTopRatedMovies(sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _viewState.value = Presenting(it)
                }, {
                    onError(R.string.network_error)
                })
        )
    }

    // sets _navigateToSelectedMovie to the selected Movie, when this is set the switch to detail page will happen
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    // sets _navigateToSelectedMovie back to null/resets it, this way when we return to overview page it doesn't automatically go back to the detail page
    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    private fun onError(message: Int) {
        _viewState.value = Error(message)
    }

}