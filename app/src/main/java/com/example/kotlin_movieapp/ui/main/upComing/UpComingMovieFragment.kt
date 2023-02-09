package com.example.kotlin_movieapp.ui.main.upComing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_movieapp.adapters.MovieAdapter
import com.example.kotlin_movieapp.databinding.FragmentUpcomingBinding
import com.example.kotlin_movieapp.model.collectionResponse.UpComingResponse
import com.example.kotlin_movieapp.ui.main.AppState.AppState
import com.example.kotlin_movieapp.ui.main.AppState.AppStateRenderer
import com.example.kotlin_movieapp.utils.init

class UpComingMovieFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) { viewModel.getUpComingCollection()}
    }

    companion object {
        fun newInstance() = UpComingMovieFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        return binding.root

    }

    private val viewModel: UpComingMovieViewModel by lazy {
        ViewModelProvider(this)[UpComingMovieViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentView = binding.upcomingFragment

        viewModel.getData().observe(viewLifecycleOwner) {
            renderData(it)
        }

        viewModel.getUpComingCollection()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) {
        dataRenderer.render(appState)
        when (appState) {

            is AppState.SuccessUpComing -> {
                initRV(appState.movieData)
            }
            else -> {return}
        }
    }

    private fun initRV(data: UpComingResponse) {
        val movieList = data.UpComingMovies
        binding.RVUpComing.init(MovieAdapter(movieList), LinearLayoutManager.HORIZONTAL)
    }
}