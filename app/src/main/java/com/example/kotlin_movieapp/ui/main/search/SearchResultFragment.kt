package com.example.kotlin_movieapp.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_movieapp.adapters.SearchMovieAdapter
import com.example.kotlin_movieapp.databinding.FragmentSearchResultBinding
import com.example.kotlin_movieapp.model.collectionResponse.SearchResponse
import com.example.kotlin_movieapp.ui.main.AppState

class SearchResultFragment (
    val appState: AppState
        ) : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderData(appState)

    }

    private fun renderData(appState : AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.SuccessSearch -> {
                binding.loadingLayout.visibility = View.GONE
                initRV(appState.movieData)
            }
            else -> {return}
        }
    }

    private fun initRV(movieData: SearchResponse) {
        val movieList = movieData.searchResults

        binding.searchRecyclerView.apply {
            adapter = SearchMovieAdapter(movieList)
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}