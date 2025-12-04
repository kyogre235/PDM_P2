package com.example.pokedex.ui.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentItemDetailBinding

class ItemDetailFragment : Fragment() {

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ItemDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = args.item

        binding.itemDetailImage.load(item.spriteUrl) {
            crossfade(true)
            placeholder(R.drawable.pokeball_placeholder)
            error(R.drawable.pokeball_placeholder)
        }

        binding.itemDetailName.text = item.name
        binding.itemDetailCost.text = item.cost.toString()
        binding.itemDetailEffect.text = item.effect
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
