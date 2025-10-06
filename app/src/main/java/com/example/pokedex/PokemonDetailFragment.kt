package com.example.pokedex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedex.databinding.FragmentPokemonDetailBinding
import com.example.pokedex.databinding.AbilityItemBinding

class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- Inicio: Código temporal para mostrar datos de ejemplo ---
        val bulbasaur = Pokemon(
            number = 1, name = "Bulbasaur", type1 = "Grass", type2 = "Poison",
            pokedexDescription = "Una extraña semilla fue plantada en su espalda al nacer. La planta brota y crece con este Pokémon.",
            abilities = listOf(Ability("Espesura", isHidden = false), Ability("Clorofila", isHidden = true)),
            stats = mapOf("HP" to 45, "Ataque" to 49, "Defensa" to 49, "Sp. Atk" to 65, "Sp. Def" to 65, "Velocidad" to 45)
        )

        // Actualizar la UI con los datos generales
        binding.detailPokemonNumber.text = "#${bulbasaur.number.toString().padStart(3, '0')}"
        binding.detailPokemonName.text = bulbasaur.name
        binding.pokedexDescriptionText.text = bulbasaur.pokedexDescription

        // Limpiar el contenedor de habilidades por si acaso
        binding.abilitiesContainer.removeAllViews()

        // Crear y añadir las vistas de habilidad dinámicamente
        val inflater = LayoutInflater.from(context)
        bulbasaur.abilities.forEach { ability ->
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
        bulbasaur.stats["HP"]?.let {
            binding.statHpBar.progress = it
            binding.statHpValue.text = it.toString()
        }
        bulbasaur.stats["Ataque"]?.let {
            binding.statAttackBar.progress = it
            binding.statAttackValue.text = it.toString()
        }
        bulbasaur.stats["Defensa"]?.let {
            binding.statDefenseBar.progress = it
            binding.statDefenseValue.text = it.toString()
        }
        bulbasaur.stats["Sp. Atk"]?.let {
            binding.statSpAtkBar.progress = it
            binding.statSpAtkValue.text = it.toString()
        }
        bulbasaur.stats["Sp. Def"]?.let {
            binding.statSpDefBar.progress = it
            binding.statSpDefValue.text = it.toString()
        }
        bulbasaur.stats["Velocidad"]?.let {
            binding.statSpeedBar.progress = it
            binding.statSpeedValue.text = it.toString()
        }
        // --- Fin: Código temporal ---
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}