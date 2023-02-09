package com.example.kotlin_movieapp.ui.main.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.kotlin_movieapp.R
import com.example.kotlin_movieapp.databinding.FragmentSearchBinding
import com.example.kotlin_movieapp.ui.main.contacts.ContactsFragment
import com.example.kotlin_movieapp.ui.main.history.HistoryFragment
import com.example.kotlin_movieapp.utils.replaceFragment
import java.util.regex.Pattern

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        setHasOptionsMenu(true)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner) {
            childFragmentManager.replaceFragment(R.id.container, SearchResultFragment(it))
        }

        binding.searchView.setOnQueryTextListener(object
            : androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                val sp = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }
                val adult = sp?.getBoolean("adult_content", true)
                val rating = sp?.getInt("searching_ratings", 5)

                if (query != null && adult == true && checkLanguage(query)) {
                    viewModel.getAdultCyrillicSearchCollection(rating, query)
                } else if (query != null && adult == true && !checkLanguage(query)) {
                    viewModel.getAdultLatinSearchCollection(rating, query)
                } else if (query != null && adult == false && checkLanguage(query)) {
                    viewModel.getCyrillicSearchCollection(rating, query)
                } else if (query != null && adult == false && !checkLanguage(query)) {
                    viewModel.getLatinSearchCollection(rating, query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                childFragmentManager.replaceFragment(R.id.container, HistoryFragment.newInstance())
                true
            }

            R.id.menu_get_contacts -> {
                childFragmentManager.replaceFragment(R.id.container, ContactsFragment.newInstance())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun checkLanguage(query: String): Boolean {
        return Pattern.matches(".*\\p{InCyrillic}.*", query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}