package com.example.kotlin_movieapp.ui.main.top250Movie

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
import com.example.kotlin_movieapp.databinding.FragmentTop250movieBinding
import com.example.kotlin_movieapp.model.collectionResponse.Top250Response
import com.example.kotlin_movieapp.ui.main.AppState
import com.google.android.material.snackbar.Snackbar

class Top250MovieFragment : Fragment() {

    private var _binding: FragmentTop250movieBinding? = null
    private val binding get() = _binding!!

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
        ViewModelProvider(this).get(Top250MovieViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        viewModel.getTop250Collection()

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.top250fragment.showSnackBar(
                    getString(R.string.data_loading_error),
                    0)
            }

            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is AppState.SuccessMovie -> {
                binding.loadingLayout.visibility = View.GONE
                initRV(appState.movieData)
            }
            else -> {return}
        }
    }

    private fun initRV(data: Top250Response) {

        val movieList = data.top250Movies

        binding.RVTop250.apply {
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