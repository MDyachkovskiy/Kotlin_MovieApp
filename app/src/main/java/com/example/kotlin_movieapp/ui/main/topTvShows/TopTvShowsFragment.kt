package com.example.kotlin_movieapp.ui.main.topTvShows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_movieapp.R
import com.example.kotlin_movieapp.adapters.MovieAdapter
import com.example.kotlin_movieapp.databinding.FragmentTvshowsBinding
import com.example.kotlin_movieapp.model.collectionResponse.TopTvShowsResponse
import com.example.kotlin_movieapp.ui.main.AppState
import com.google.android.material.snackbar.Snackbar

class TopTvShowsFragment : Fragment() {

    private var _binding: FragmentTvshowsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = TopTvShowsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentTvshowsBinding.inflate(inflater, container, false)

        return binding.root

    }

    private val viewModel: TopTvShowsViewModel by lazy {
        ViewModelProvider(this).get(TopTvShowsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        viewModel.getTvShowsCollection()

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.tvshowsfragment.showSnackBar(
                    getString(R.string.data_loading_error),
                    0)
            }

            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is AppState.SuccessTvShow -> {
                binding.loadingLayout.visibility = View.GONE
                initRV(appState.movieData)
            }
            else -> {return }
        }
    }

    private fun initRV(data: TopTvShowsResponse) {

        val movieList = data.topTvShows

        binding.RVTvShows.apply {
            adapter = MovieAdapter(movieList)
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false)
        }
    }

    private fun View.showSnackBar(
        text: String,
        length: Int = Snackbar.LENGTH_INDEFINITE,
    ) {
        Snackbar.make(this, text, length).show()
    }
}