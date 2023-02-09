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
import com.example.kotlin_movieapp.ui.main.AppState.AppState
import com.example.kotlin_movieapp.ui.main.AppState.AppStateRenderer
import com.example.kotlin_movieapp.utils.init

class SearchResultFragment (
    val appState: AppState
        ) : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) {} }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentView = binding.searchResultFragment

        renderData(appState)
    }

    private fun renderData(appState : AppState) {
        dataRenderer.render(appState)
        when (appState) {
            is AppState.SuccessSearch -> {
                initRV(appState.movieData)
            }
            else -> {return}
        }
    }

    private fun initRV(movieData: SearchResponse) {
        val movieList = movieData.searchResults
        binding.searchRecyclerView.init(SearchMovieAdapter(movieList), LinearLayoutManager.VERTICAL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}