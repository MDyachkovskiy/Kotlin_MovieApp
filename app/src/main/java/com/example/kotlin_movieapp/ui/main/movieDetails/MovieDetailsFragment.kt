package com.example.kotlin_movieapp.ui.main.movieDetails

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kotlin_movieapp.R
import com.example.kotlin_movieapp.adapters.PersonsAdapter
import com.example.kotlin_movieapp.databinding.FragmentMovieDetailBinding
import com.example.kotlin_movieapp.model.collectionResponse.CollectionItem
import com.example.kotlin_movieapp.model.movieDetailsResponse.MovieDTO
import com.example.kotlin_movieapp.model.movieDetailsResponse.Person
import com.example.kotlin_movieapp.ui.main.AppState.AppStateRenderer
import com.example.kotlin_movieapp.ui.main.AppState.DetailsState
import com.example.kotlin_movieapp.utils.*

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieBundle : CollectionItem
    private lateinit var movie : MovieDTO

    private lateinit var parentView: View

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) { viewModel.getMovieFromRemoteSource(movieBundle.kinopoiskId) }
    }

    private val viewModel: MovieDetailsViewModel by lazy {
        ViewModelProvider(this)[MovieDetailsViewModel::class.java]
    }

    companion object {
        fun newInstance(bundle: Bundle) : MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentView = binding.movieDetail

        binding.movieDetail.visibility = View.VISIBLE
        binding.includedLoadingLayout.loadingLayout.visibility = View.VISIBLE

        binding.userNote.addTextChangedListener (object : TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
              Thread{
                  addCommentToMovie(movie, text)
              }.start()

            }
        })

        binding.favorite.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                Thread {
                    saveFavoriteMovie(movie)
                }.start()
                binding.movieDetail.showToast(getString(R.string.addMovieToFavorite))
            } else {
                Thread {
                    deleteFavoriteMovie(movie)
                }.start()
                binding.movieDetail.showToast(getString(R.string.deleteMovieFromFavorite))
            }
        }

        movieBundle = arguments?.getParcelable(KEY_BUNDLE_MOVIE) ?: CollectionItem()

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }

        requestMovieDetail(movieBundle.kinopoiskId)
    }

    private fun requestMovieDetail(movieId: Int?) {
        viewModel.getMovieFromRemoteSource(movieId)
    }

    private fun renderData(appState: DetailsState) {
        dataRenderer.render(appState)

        when (appState) {
            is DetailsState.SuccessMovie -> {
                movie = appState.movieDTO
                displayMovie(appState.movieDTO)
            }
            else -> return
        }
    }

    private fun displayMovie(movieDTO : MovieDTO) {

        Thread{
            val date = System.currentTimeMillis()
            saveMovie(movieDTO, date)
        }.start()

        with(binding) {

            movieDescription.text = movieDTO.description
            movieTitle.text = movieDTO.name

            view?.let {
                Glide.with(it).load(movieDTO.poster?.url).into(moviePoster) }

            initPersonsRecyclerView(movieDTO.persons)

            movieReleaseDate.text = movieDTO.year.toString()
            movieLength.text = getString(R.string.movieLength, movieDTO.movieLength.toString())
            movieBudget.text = getString(R.string.movieBudget,
                movieDTO.budget?.value?.toString(), movieDTO.budget?.currency)
            movieKpRating.text = movieDTO.rating?.kp.toString()
            movieImdbRating.text = movieDTO.rating?.imdb.toString()

            movieGenres.text = movieDTO.genres?.convert { genre -> genre.name }

            movieCountry.text = movieDTO.countries?.convert {country -> country.name}
        }
    }

    private fun initPersonsRecyclerView (persons: List <Person>) {
        val actors = persons.filter {
            it.enProfession == "actor"
        }
        val movieStaff = persons.filter{
            it.enProfession != "actor"
        }

        binding.actorsRV.init(PersonsAdapter(actors), LinearLayoutManager.HORIZONTAL)
        binding.movieStaffRV.init(PersonsAdapter(movieStaff), LinearLayoutManager.HORIZONTAL)
    }

    private fun saveMovie(movieDTO: MovieDTO, date: Long) {
        viewModel.saveMovieToDB(movieDTO, date)
    }

    private fun saveFavoriteMovie (movieDTO: MovieDTO) {
        viewModel.saveFavoriteMovieToDB(movieDTO)
    }

    private fun deleteFavoriteMovie (movieDTO: MovieDTO) {
        viewModel.saveFavoriteMovieToDB(movieDTO)
    }

    private fun addCommentToMovie(movieDTO: MovieDTO, text: Editable?){
        viewModel.addCommentToMovie(movieDTO, text)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}