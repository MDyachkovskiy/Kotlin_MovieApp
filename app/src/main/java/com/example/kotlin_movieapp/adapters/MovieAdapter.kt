package com.example.kotlin_movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_movieapp.databinding.ItemMovieBinding
import com.example.kotlin_movieapp.model.collectionResponse.CollectionItem
import com.example.kotlin_movieapp.ui.main.movieDetails.MovieDetailsFragment
import com.example.kotlin_movieapp.utils.KEY_BUNDLE_MOVIE
import com.example.kotlin_movieapp.utils.openDetailsFragment
import com.squareup.picasso.Picasso

class MovieAdapter(
    private var movieData: List<CollectionItem>
    ) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int = movieData.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: CollectionItem) {
            val binding = ItemMovieBinding.bind(itemView)
            with(binding) {

                Picasso.get()?.load(movie.poster?.previewUrl)?.into(moviePoster)

                root.setOnClickListener {
                    it.openDetailsFragment(
                        MovieDetailsFragment::class.java,
                        KEY_BUNDLE_MOVIE,
                        movie
                    )}
            }
        }
    }
}