package com.example.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.PokemonItemBinding

// Nuevo modelo para una habilidad
data class Ability(val name: String, val isHidden: Boolean)

// Modelo de datos ampliado para incluir detalles del Pokémon
data class Pokemon(
    val number: Int,
    val name: String,
    val type1: String,
    val type2: String? = null,
    val pokedexDescription: String,
    val abilities: List<Ability>, // Usamos la nueva clase Ability
    val stats: Map<String, Int>
)

class PokemonAdapter(
    private val pokemonList: List<Pokemon>,
    private val onItemClicked: (Pokemon) -> Unit // Listener para clics en los elementos
) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
        // Configura el OnClickListener para el elemento de la lista
        holder.itemView.setOnClickListener {
            onItemClicked(pokemon)
        }
    }

    override fun getItemCount() = pokemonList.size

    class PokemonViewHolder(private val binding: PokemonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.pokemonNumber.text = "#${pokemon.number.toString().padStart(3, '0')}"
            binding.pokemonName.text = pokemon.name
            binding.pokemonType1.text = pokemon.type1
            if (pokemon.type2 != null) {
                binding.pokemonType2.visibility = View.VISIBLE
                binding.pokemonType2.text = pokemon.type2
            } else {
                binding.pokemonType2.visibility = View.GONE
            }
            // Cargaremos la imagen más tarde
        }
    }
}