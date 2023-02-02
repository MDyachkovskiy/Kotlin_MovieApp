package com.example.kotlin_movieapp.ui.main.favoritesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_movieapp.adapters.FavoriteMovieAdapter
import com.example.kotlin_movieapp.databinding.FragmentFavoritesBinding
import com.example.kotlin_movieapp.model.room.favorites.FavoriteMovieItem
import com.example.kotlin_movieapp.ui.main.AppState

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by lazy {
        ViewModelProvider (this).get (FavoritesViewModel::class.java)
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

        binding.loadingLayout.visibility = View.VISIBLE

        Thread {
            viewModel.getAllFavorites()
        }.start()

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
    }

    private fun renderData(appState : AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.SuccessFavorites -> {
                binding.loadingLayout.visibility = View.GONE
                initRV(appState.movieData)
            }
            else -> {return}
        }
    }

    private fun initRV(movieData: List<FavoriteMovieItem>) {

        binding.recyclerView.apply {
            adapter = FavoriteMovieAdapter(movieData)
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