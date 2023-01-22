package com.example.kotlin_movieapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_movieapp.MainActivity
import com.example.kotlin_movieapp.R
import com.example.kotlin_movieapp.databinding.SearchMovieItemBinding
import com.example.kotlin_movieapp.model.collectionResponse.CollectionItem
import com.example.kotlin_movieapp.ui.main.movieDetails.MovieDetailsFragment
import com.example.kotlin_movieapp.utils.KEY_BUNDLE_MOVIE
import com.squareup.picasso.Picasso

class SearchMovieAdapter(
    private var movieData: List<CollectionItem>
    ) : RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchMovieItemBinding.inflate(LayoutInflater.from(parent.context),
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
            val binding = SearchMovieItemBinding.bind(itemView)
            with(binding) {

                movieTitle.text = movie.name

                movieDescription.text = movie.description

                Picasso.get()?.load(movie.poster?.previewUrl)?.into(poster)

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