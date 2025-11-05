package com.example.pokedex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.pokedex.databinding.FragmentPokemonDetailBinding
import com.example.pokedex.databinding.AbilityItemBinding

class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private val args: PokemonDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemon = args.pokemon

        /*
        * ahora cargamos la imagen del pokemon con la libreria coil
        * */
        binding.detailPokemonImage.load(pokemon.spriteUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_background)
        }

        // Actualizar la UI con los datos generales
        binding.detailPokemonNumber.text = "#${pokemon.number.toString().padStart(3, '0')}"
        binding.detailPokemonName.text = pokemon.name
        binding.pokedexDescriptionText.text = pokemon.pokedexDescription

        // Limpiar el contenedor de habilidades por si acaso
        binding.abilitiesContainer.removeAllViews()

        // Crear y añadir las vistas de habilidad dinámicamente
        val inflater = LayoutInflater.from(context)
        pokemon.abilities.forEach { ability ->
            val abilityBinding = AbilityItemBinding.inflate(inflater, binding.abilitiesContainer, false)
            abilityBinding.abilityNameText.text = ability.name
            if (ability.isHidden) {
                abilityBinding.hiddenAbilityTag.visibility = View.VISIBLE
            } else {
                abilityBinding.hiddenAbilityTag.visibility = View.GONE
            }
            binding.abilitiesContainer.addView(abilityBinding.root)
        }

        // Rellenar todas las estadísticas
        pokemon.stats["HP"]?.let {
            binding.statHpBar.progress = it
            binding.statHpValue.text = it.toString()
        }
        pokemon.stats["Ataque"]?.let {
            binding.statAttackBar.progress = it
            binding.statAttackValue.text = it.toString()
        }
        pokemon.stats["Defensa"]?.let {
            binding.statDefenseBar.progress = it
            binding.statDefenseValue.text = it.toString()
        }
        pokemon.stats["Sp. Atk"]?.let {
            binding.statSpAtkBar.progress = it
            binding.statSpAtkValue.text = it.toString()
        }
        pokemon.stats["Sp. Def"]?.let {
            binding.statSpDefBar.progress = it
            binding.statSpDefValue.text = it.toString()
        }
        pokemon.stats["Velocidad"]?.let {
            binding.statSpeedBar.progress = it
            binding.statSpeedValue.text = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}