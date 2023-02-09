package com.example.kotlin_movieapp.ui.main.topTvShows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_movieapp.adapters.MovieAdapter
import com.example.kotlin_movieapp.databinding.FragmentTvshowsBinding
import com.example.kotlin_movieapp.model.collectionResponse.TopTvShowsResponse
import com.example.kotlin_movieapp.ui.main.AppState.AppState
import com.example.kotlin_movieapp.ui.main.AppState.AppStateRenderer
import com.example.kotlin_movieapp.utils.init

class TopTvShowsFragment : Fragment() {

    private var _binding: FragmentTvshowsBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) {viewModel.getTvShowsCollection()}
    }

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
        ViewModelProvider(this)[TopTvShowsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentView = binding.tvShowsFragment

        viewModel.getData().observe(viewLifecycleOwner) {
            renderData(it)
        }

        viewModel.getTvShowsCollection()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) {
        dataRenderer.render(appState)

        when (appState) {

            is AppState.SuccessTvShow -> {
                initRV(appState.movieData)
            }
            else -> {return }
        }
    }

    private fun initRV(data: TopTvShowsResponse) {
        val movieList = data.topTvShows
        binding.RVTvShows.init(MovieAdapter(movieList), LinearLayoutManager.HORIZONTAL)
    }
}