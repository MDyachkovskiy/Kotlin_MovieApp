package com.example.kotlin_movieapp.ui.main.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlin_movieapp.R
import com.example.kotlin_movieapp.databinding.FragmentMovieListBinding
import com.example.kotlin_movieapp.ui.main.top250Movie.Top250MovieFragment
import com.example.kotlin_movieapp.ui.main.topTvShows.TopTvShowsFragment
import com.example.kotlin_movieapp.ui.main.upComing.UpComingMovieFragment

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MovieListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentMovieListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.container_UpComing, UpComingMovieFragment.newInstance())
            .replace(R.id.container_Top250, Top250MovieFragment.newInstance())
            .replace(R.id.container_TvShows, TopTvShowsFragment.newInstance())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}