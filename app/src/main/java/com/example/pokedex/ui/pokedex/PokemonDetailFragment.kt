package com.example.pokedex.ui.pokedex

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonDetailBinding
import com.example.pokedex.databinding.AbilityItemBinding
import com.example.pokedex.model.Pokemon

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

        binding.detailPokemonImage.load(pokemon.spriteUrl) {
            crossfade(true)
            placeholder(R.drawable.pokeball_placeholder)
            error(R.drawable.pokeball_placeholder)
        }

        binding.detailPokemonNumber.text = "#${pokemon.number.toString().padStart(3, '0')}"
        binding.detailPokemonName.text = pokemon.name
        binding.pokedexDescriptionText.text = pokemon.pokedexDescription

        binding.abilitiesContainer.removeAllViews()

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

        pokemon.stats["HP"]?.let {
            setStatBar(binding.statHpBar, binding.statHpValue, it, R.color.stat_hp)
        }
        pokemon.stats["Ataque"]?.let {
            setStatBar(binding.statAttackBar, binding.statAttackValue, it, R.color.stat_attack)
        }
        pokemon.stats["Defensa"]?.let {
            setStatBar(binding.statDefenseBar, binding.statDefenseValue, it, R.color.stat_defense)
        }
        pokemon.stats["Sp. Atk"]?.let {
            setStatBar(binding.statSpAtkBar, binding.statSpAtkValue, it, R.color.stat_sp_atk)
        }
        pokemon.stats["Sp. Def"]?.let {
            setStatBar(binding.statSpDefBar, binding.statSpDefValue, it, R.color.stat_sp_def)
        }
        pokemon.stats["Velocidad"]?.let {
            setStatBar(binding.statSpeedBar, binding.statSpeedValue, it, R.color.stat_speed)
        }
    }

    private fun setStatBar(progressBar: ProgressBar, valueView: android.widget.TextView, value: Int, colorRes: Int) {
        progressBar.progress = value
        valueView.text = value.toString()

        val color = ContextCompat.getColor(requireContext(), colorRes)
        progressBar.progressTintList = ColorStateList.valueOf(color)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}