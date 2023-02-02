package com.example.kotlin_movieapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_movieapp.ui.main.MainActivity
import com.example.kotlin_movieapp.R
import com.example.kotlin_movieapp.databinding.ItemHistoryMovieBinding
import com.example.kotlin_movieapp.model.room.history.HistoryMovieItem
import com.example.kotlin_movieapp.ui.main.movieDetails.MovieDetailsFragment
import com.example.kotlin_movieapp.utils.KEY_BUNDLE_MOVIE
import com.example.kotlin_movieapp.utils.convertHistoryMovieItemToCollectionItem
import com.squareup.picasso.Picasso
import java.util.*

class HistoryMovieAdapter(
    private var movieData: List<HistoryMovieItem>
    ) : RecyclerView.Adapter<HistoryMovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryMovieBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int = movieData.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: HistoryMovieItem) {
            val binding = ItemHistoryMovieBinding.bind(itemView)
            with(binding) {

                movieTitle.text = movie.name

                movieDescription.text = movie.description

                Picasso.get()?.load(movie.poster)?.into(poster)

                val viewingDate =Date(movie.date)
                date.text = viewingDate.toString()

                userNote.text = movie.userNote

                root.setOnClickListener {
                    (itemView.context as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .addToBackStack("tag")
                        .add(R.id.main_container,
                            MovieDetailsFragment.newInstance(Bundle().apply {
                                putParcelable(KEY_BUNDLE_MOVIE,
                                    convertHistoryMovieItemToCollectionItem(movie))
                            }))
                        .commit()
                }
            }
        }
    }
}