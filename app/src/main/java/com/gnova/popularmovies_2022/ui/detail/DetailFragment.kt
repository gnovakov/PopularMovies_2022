package com.gnova.popularmovies_2022.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gnova.domain.models.Movie
import com.gnova.domain.models.Trailer
import com.gnova.popularmovies_2022.common.Const.BASE_IMAGE_LARGE
import com.gnova.popularmovies_2022.common.Const.YOUTUBE_TRAILER_BASE_URL
import com.gnova.popularmovies_2022.ui.detail.DetailViewState.*
import com.gnova.popularmovies_2022.R
import com.gnova.popularmovies_2022.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()

    private val trailerViewAdapter: TrailerViewAdapter by lazy {
        TrailerViewAdapter(TrailerViewAdapter.OnClickListener {
            trailerClick(it)
        })
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        _binding = binding

        // Grab the selectedMovie from the safeargs
        val movie = DetailFragmentArgs.fromBundle(requireArguments()).selectedMovie

        // Init View Model
        viewModel.onViewInit(movie)

        setupRecyclerView()

        observeViewState()

    }

    private fun observeViewState() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Loading -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.loading_animation)
                }
                is Error -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    binding.networkErrorTv.visibility = View.VISIBLE
                    binding.networkErrorTv.text = resources.getString(it.message)
                }
                is Presenting -> {
                    observeSelectedMovie()
                    showTrailers(it.results)
                }

            }
        })

    }

    private fun observeSelectedMovie() {
        viewModel.selectedMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                initialiseData(it)
            }
        })
    }

    private fun showTrailers(trailers: List<Trailer>) {
        trailerViewAdapter.submitList(trailers)
    }

    private fun initialiseData(movie: Movie) {
        movie.poster_path?.let { picassoLoadImages(it, binding.moviePoster) }
        movie.backdrop_path?.let { picassoLoadImages(it, binding.movieBackdrop) }
        binding.releaseDate.text = movie.release_date
        binding.rating.text = movie.vote_average.toString()
        binding.movieTitle.text = movie.title
        binding.synopsis.text = movie.overview
    }

    private fun trailerClick(it: Trailer) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(YOUTUBE_TRAILER_BASE_URL + it.key)
        startActivity(openURL)
    }

    private fun setupRecyclerView() {
        binding.trailerRecyclerView.setHasFixedSize(true)
        binding.trailerRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.trailerRecyclerView.adapter = trailerViewAdapter
    }


    private fun picassoLoadImages(img: String, imageView: ImageView) {
        val imgUrl = BASE_IMAGE_LARGE + img
        imgUrl.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Picasso.get()
                .load(imgUri)
                .error(R.drawable.ic_broken_image)
                .into(imageView)
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}