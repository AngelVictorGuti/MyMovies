package com.angelvictor.movies.ui.billboard

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angelvictor.movies.R
import com.angelvictor.movies.databinding.ViewMovieBinding
import com.angelvictor.movies.ui.model.MovieUi
import com.angelvictor.movies.ui.common.inflate
import com.angelvictor.movies.ui.common.loadUrl

class MoviesAdapter(
    private var movies: List<MovieUi>,
    private val onClickListener: (MovieUi) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    fun updateMovies(newMovies: List<MovieUi>) {
        this.movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_movie, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { onClickListener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewMovieBinding.bind(view)
        fun bind(movie: MovieUi) {
            binding.apply {
                ivMovie.loadUrl(movie.posterPath)
                movieTitle.text = movie.title
                releaseDate.text = movie.releaseDate
            }
        }
    }

    override fun getItemCount(): Int = movies.size
}