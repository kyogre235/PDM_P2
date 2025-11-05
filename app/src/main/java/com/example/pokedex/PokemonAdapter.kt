package com.example.pokedex

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.databinding.PokemonItemBinding
import kotlinx.parcelize.Parcelize

// Nuevo modelo para una habilidad
@Parcelize
data class Ability(val name: String, val isHidden: Boolean) : Parcelable // Es parcelable para poder pasarlo entre fragmentos

//modelo de datos de los pokemon
@Parcelize
data class Pokemon(
    val number: Int, // munero de pokedex
    val name: String, // nombre
    val spriteUrl: String, // URL del sprite del Pokemon
    val type1: String, // tipo primario
    val type2: String? = null, // tipo secunadrio, puede no tenerlo, por eso puede ser null
    val pokedexDescription: String, // entrada de la pokedex
    val abilities: List<Ability>, // habilidades del pokemon
    val stats: Map<String, Int> // estadisticas del pokemon (HP,ATK,DEF,ATK ESP,DEF ESP,SPD)
) : Parcelable // Es parcelable para poder pasarlo entre fragmentos

class PokemonAdapter(
    private var pokemonList: List<Pokemon>,
    private val onItemClicked: (Pokemon) -> Unit
) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    /*
    * actualiza la lista de pokemon y notifica al adaptador que los datos han cambiado
    * */
    fun updateData(newPokemonList: List<Pokemon>) {
        pokemonList = newPokemonList
        notifyDataSetChanged()
    }

    /*
    * Crea los viewholders para cada item de la lista
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

   /*
   * Enlaza los datos del pokemon con el viewholder
   * */
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            onItemClicked(pokemon)
        }
    }

    /*
    * da el tamaño de la lista de pokemon
    * */
    override fun getItemCount() = pokemonList.size

    /*
    * Definimos el viewholder para cada item de la lista y usamos
    * RecyclerView.ViewHolder(binding.root) para que se muestre en la pantalla
    * */
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

            // Cargamos cada sprite de pokemon con la libreria coil
            binding.pokemonImage.load(pokemon.spriteUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background) // aqui va un placeholder para cuando la imagen carga (luego la pongo, xd)
                error(R.drawable.ic_launcher_background) //aqui va una imagen de error, luego la pongo, XD
            }
        }
    }
}