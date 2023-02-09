package com.example.kotlin_movieapp.ui.main.favoritesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_movieapp.adapters.FavoriteMovieAdapter
import com.example.kotlin_movieapp.databinding.FragmentFavoritesBinding
import com.example.kotlin_movieapp.model.room.favorites.FavoriteMovieItem
import com.example.kotlin_movieapp.ui.main.AppState.AppState
import com.example.kotlin_movieapp.ui.main.AppState.AppStateRenderer
import com.example.kotlin_movieapp.utils.init

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) {viewModel.getAllFavorites()}
    }

    private val viewModel: FavoritesViewModel by lazy {
        ViewModelProvider (this)[FavoritesViewModel::class.java]
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentView = binding.favoritesFragment

        binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE

        Thread {
            viewModel.getAllFavorites()
        }.start()

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    private fun renderData(appState : AppState) {

        dataRenderer.render(appState)

        when (appState) {
            is AppState.SuccessFavorites -> {
                initRV(appState.movieData)
            }
            else -> {return}
        }
    }

    private fun initRV(movieData: List<FavoriteMovieItem>) {
        binding.recyclerView.init(FavoriteMovieAdapter(movieData), LinearLayoutManager.VERTICAL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}