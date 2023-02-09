package com.example.kotlin_movieapp.ui.main.top250Movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_movieapp.adapters.MovieAdapter
import com.example.kotlin_movieapp.databinding.FragmentTop250movieBinding
import com.example.kotlin_movieapp.model.collectionResponse.Top250Response
import com.example.kotlin_movieapp.ui.main.AppState.AppState
import com.example.kotlin_movieapp.ui.main.AppState.AppStateRenderer
import com.example.kotlin_movieapp.utils.init

class Top250MovieFragment : Fragment() {

    private var _binding: FragmentTop250movieBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) {viewModel.getTop250Collection()}
    }


    companion object {
        fun newInstance() = Top250MovieFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentTop250movieBinding.inflate(inflater, container, false)

        return binding.root
    }

    private val viewModel: Top250MovieViewModel by lazy {
        ViewModelProvider(this)[Top250MovieViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentView = binding.top250fragment

        viewModel.getData().observe(viewLifecycleOwner) {
            renderData(it)
        }

        viewModel.getTop250Collection()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) {
        dataRenderer.render(appState)

        when (appState) {

            is AppState.SuccessMovie -> {
                initRV(appState.movieData)
            }
            else -> { return }
        }
    }

    private fun initRV(data: Top250Response) {
        val movieList = data.top250Movies
        binding.RVTop250.init(MovieAdapter(movieList), LinearLayoutManager.HORIZONTAL)
    }
}