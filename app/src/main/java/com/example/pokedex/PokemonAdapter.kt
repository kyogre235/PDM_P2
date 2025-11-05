package com.example.pokedex

import android.graphics.drawable.GradientDrawable
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.databinding.PokemonItemBinding
import kotlinx.parcelize.Parcelize

// Nuevo modelo para una habilidad
@Parcelize
data class Ability(val name: String, val isHidden: Boolean) : Parcelable

//modelo de datos de los pokemon
@Parcelize
data class Pokemon(
    val number: Int,
    val name: String,
    val spriteUrl: String,
    val type1: String,
    val type2: String? = null,
    val pokedexDescription: String,
    val abilities: List<Ability>,
    val stats: Map<String, Int>
) : Parcelable

class PokemonAdapter(
    private var pokemonList: List<Pokemon>,
    private val onItemClicked: (Pokemon) -> Unit
) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    fun updateData(newPokemonList: List<Pokemon>) {
        pokemonList = newPokemonList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            onItemClicked(pokemon)
        }
    }

    override fun getItemCount() = pokemonList.size

    class PokemonViewHolder(private val binding: PokemonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.pokemonNumber.text = "#${pokemon.number.toString().padStart(3, '0')}"
            binding.pokemonName.text = pokemon.name

            // Configurar tipo 1 con color
            binding.pokemonType1.text = pokemon.type1
            setTypeBackground(binding.pokemonType1, pokemon.type1)

            // Configurar tipo 2 si existe
            if (pokemon.type2 != null) {
                binding.pokemonType2.visibility = View.VISIBLE
                binding.pokemonType2.text = pokemon.type2
                setTypeBackground(binding.pokemonType2, pokemon.type2)
            } else {
                binding.pokemonType2.visibility = View.GONE
            }

            // Cargar sprite con placeholder de Pokéball
            binding.pokemonImage.load(pokemon.spriteUrl) {
                crossfade(true)
                placeholder(R.drawable.pokeball_placeholder)
                error(R.drawable.pokeball_placeholder)
            }
        }

        private fun setTypeBackground(view: View, typeName: String) {
            val colorRes = when (typeName.lowercase()) {
                "normal" -> R.color.type_normal
                "fire", "fuego" -> R.color.type_fire
                "water", "agua" -> R.color.type_water
                "electric", "eléctrico" -> R.color.type_electric
                "grass", "planta" -> R.color.type_grass
                "ice", "hielo" -> R.color.type_ice
                "fighting", "lucha" -> R.color.type_fighting
                "poison", "veneno" -> R.color.type_poison
                "ground", "tierra" -> R.color.type_ground
                "flying", "volador" -> R.color.type_flying
                "psychic", "psíquico" -> R.color.type_psychic
                "bug", "bicho" -> R.color.type_bug
                "rock", "roca" -> R.color.type_rock
                "ghost", "fantasma" -> R.color.type_ghost
                "dragon", "dragón" -> R.color.type_dragon
                "dark", "siniestro" -> R.color.type_dark
                "steel", "acero" -> R.color.type_steel
                "fairy", "hada" -> R.color.type_fairy
                else -> R.color.type_normal
            }

            val color = ContextCompat.getColor(view.context, colorRes)
            val drawable = GradientDrawable().apply {
                setColor(color)
                cornerRadius = 12f
            }
            view.background = drawable
        }
    }
}