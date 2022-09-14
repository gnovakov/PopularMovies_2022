package com.gnova.popularmovies_2022.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gnova.domain.models.Movie
import com.gnova.popularmovies_2022.Const.BASE_IMAGE_LARGE
import com.gnova.popularmovies_2022.R
import com.gnova.popularmovies_2022.databinding.MovieGridViewItemBinding
import com.squareup.picasso.Picasso

class MovieAdapter(private val onClickListener: OnClickListener) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(MovieGridViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder( holder: MovieViewHolder, position: Int) {
        val movies = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movies)
        }
        holder.bind(movies)
    }

    class MovieViewHolder(val binding: MovieGridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {

            val imgUrl = BASE_IMAGE_LARGE + movie.poster_path
            imgUrl.let {
                val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
                Picasso.get()
                    .load(imgUri)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(binding.movieImage)
            }

        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }

}