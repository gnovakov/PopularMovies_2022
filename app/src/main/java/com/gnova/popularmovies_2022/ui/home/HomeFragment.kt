package com.gnova.popularmovies_2022.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gnova.domain.models.Movie
import com.gnova.popularmovies_2022.R
import com.gnova.popularmovies_2022.databinding.FragmentHomeBinding
import com.gnova.popularmovies_2022.ui.home.HomeViewState.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private val adapter: MovieAdapter by lazy {
        MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it) // Set the Movie to the _navigateToSelectedMovie Live Data
        })
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        _binding = binding

        setupRecyclerView()

        viewModel.onViewLoaded()

        observeViewState()
        observeClick()
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Loading -> {
                    Log.d("TAG", "LOADING")
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.loading_animation)
                }
                is Error -> {
                    Log.d("TAG", "ERROR HOME FRAGMENT")
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    binding.networkErrorTv.visibility = View.VISIBLE
                    binding.networkErrorTv.text = resources.getString(it.message)
                }
                is Presenting -> {
                    binding.statusImage.visibility = View.GONE
                    showMovies(it.results)
                }

            }
        })
    }

    private fun observeClick() {
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if (it != null) { //Open the Detail Fragment if _navigateToSelectedMovie is not Null
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                )
                viewModel.displayMovieDetailsComplete() // Clear the _navigateToSelectedMovie after the Detail fragment is opened
            }
        })
    }

    private fun showMovies(movies: List<Movie>) {

        adapter.submitList(movies)
    }

    private fun setupRecyclerView() {
        Log.d("TAG", "setupRecyclerView")
        binding.movieRecyclerView.setHasFixedSize(true)
        binding.movieRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        binding.movieRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}