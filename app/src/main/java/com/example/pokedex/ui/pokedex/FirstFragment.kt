package com.example.pokedex.ui.pokedex

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.adapter.PokemonAdapter
import com.example.pokedex.databinding.FragmentFirstBinding
import com.example.pokedex.db.PokemonDatabase
import com.example.pokedex.repositorio.PokemonRepository
import com.example.pokedex.viewModel.PokemonViewModel
import com.example.pokedex.viewModel.PokemonViewModelFactory

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var searchView: SearchView

    private val pokemonViewModel: PokemonViewModel by viewModels {
        val database = PokemonDatabase.getDatabase(requireContext())
        val repository = PokemonRepository(database.pokemonDao())
        PokemonViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        if (pokemonViewModel.pokemonList.value.isNullOrEmpty()) {
            pokemonViewModel.loadPokemon()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                pokemonViewModel.searchPokemon(newText.orEmpty())
                return true
            }
        })

        searchView.setOnCloseListener {
            pokemonViewModel.loadPokemon()
            false
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupRecyclerView() {
        pokemonAdapter = PokemonAdapter(emptyList()) { pokemon ->
            val action = FirstFragmentDirections.actionFirstFragmentToPokemonDetailFragment(pokemon)
            findNavController().navigate(action)
        }
        binding.pokemonRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.pokemonRecyclerview.adapter = pokemonAdapter
    }

    private fun observeViewModel() {
        pokemonViewModel.pokemonList.observe(viewLifecycleOwner) { pokemonList ->
            pokemonAdapter.updateData(pokemonList)
        }

        pokemonViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.pokemonRecyclerview.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
