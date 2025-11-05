package com.example.pokedex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentFirstBinding
import com.example.pokedex.lector.PokeApi
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemonAdapter: PokemonAdapter

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
        loadPokemonData()
    }

    private fun setupRecyclerView() {
        pokemonAdapter = PokemonAdapter(emptyList()) { pokemon ->
            val action = FirstFragmentDirections.actionFirstFragmentToPokemonDetailFragment(pokemon)
            findNavController().navigate(action)
        }
        binding.pokemonRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.pokemonRecyclerview.adapter = pokemonAdapter
    }

    /*
    * Carga los datos de los pokemon desde la clase PokeApi,
    * como las peticiones no son inmediatas, pone el círculo de cargando
    * hasta que tenga todas las peticiones
    * */
    private fun loadPokemonData() {
        lifecycleScope.launch {
            showLoading(true)
            val pokeApi = PokeApi()
            val pokemonList = pokeApi.getPokemonList()
            pokemonAdapter.updateData(pokemonList)
            showLoading(false)
        }
    }

    /*
    * Con esta función determinamos si se muestra el círculo de cargando o no
    * */
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.pokemonRecyclerview.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}