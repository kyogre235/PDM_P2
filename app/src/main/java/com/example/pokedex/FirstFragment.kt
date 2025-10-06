package com.example.pokedex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Crear datos de ejemplo con el nuevo modelo de Habilidad
        val pokemonList = listOf(
            Pokemon(
                number = 1, name = "Bulbasaur", type1 = "Grass", type2 = "Poison",
                pokedexDescription = "Una extraña semilla fue plantada en su espalda al nacer. La planta brota y crece con este Pokémon.",
                abilities = listOf(Ability("Espesura", isHidden = false), Ability("Clorofila", isHidden = true)),
                stats = mapOf("HP" to 45, "Ataque" to 49, "Defensa" to 49, "Sp. Atk" to 65, "Sp. Def" to 65, "Velocidad" to 45)
            ),
            Pokemon(
                number = 4, name = "Charmander", type1 = "Fire",
                pokedexDescription = "Prefiere los lugares calientes. Dicen que cuando llueve, le sale vapor de la punta de la cola.",
                abilities = listOf(Ability("Mar Llamas", isHidden = false), Ability("Poder Solar", isHidden = true)),
                stats = mapOf("HP" to 39, "Ataque" to 52, "Defensa" to 43, "Sp. Atk" to 60, "Sp. Def" to 50, "Velocidad" to 65)
            ),
            Pokemon(
                number = 7, name = "Squirtle", type1 = "Water",
                pokedexDescription = "Cuando retrae su largo cuello en el caparazón, dispara agua a una presión increíble.",
                abilities = listOf(Ability("Torrente", isHidden = false), Ability("Cura Lluvia", isHidden = true)),
                stats = mapOf("HP" to 44, "Ataque" to 48, "Defensa" to 65, "Sp. Atk" to 50, "Sp. Def" to 64, "Velocidad" to 43)
            )
        )

        val adapter = PokemonAdapter(pokemonList) { pokemon ->
            Snackbar.make(view, pokemon.name, Snackbar.LENGTH_SHORT).show()
        }

        binding.pokemonRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.pokemonRecyclerview.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}