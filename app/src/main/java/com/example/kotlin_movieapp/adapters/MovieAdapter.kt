package com.example.kotlin_movieapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_movieapp.ui.main.MainActivity
import com.example.kotlin_movieapp.R
import com.example.kotlin_movieapp.databinding.ItemMovieBinding
import com.example.kotlin_movieapp.model.collectionResponse.CollectionItem
import com.example.kotlin_movieapp.ui.main.movieDetails.MovieDetailsFragment
import com.example.kotlin_movieapp.utils.KEY_BUNDLE_MOVIE
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
                    (itemView.context as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .addToBackStack("tag")
                        .add(R.id.main_container,
                            MovieDetailsFragment.newInstance(Bundle().apply {
                                putParcelable(KEY_BUNDLE_MOVIE, movie)
                            }))
                        .commit()
                }
            }
        }
    }
}