package com.example.pokedex.ui.item

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.adapter.ItemAdapter
import com.example.pokedex.databinding.FragmentItemListBinding
import com.example.pokedex.db.PokemonDatabase
import com.example.pokedex.repositorio.ItemRepository
import com.example.pokedex.viewModel.ItemViewModel
import com.example.pokedex.viewModel.ItemViewModelFactory

class ItemListFragment : Fragment() {

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var searchView: SearchView

    private val itemViewModel: ItemViewModel by viewModels {
        val database = PokemonDatabase.getDatabase(requireContext())
        val repository = ItemRepository(database.itemDao())
        ItemViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        if (itemViewModel.itemList.value.isNullOrEmpty()) {
            itemViewModel.loadItems()
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
                itemViewModel.searchItem(newText.orEmpty())
                return true
            }
        })

        searchView.setOnCloseListener {
            itemViewModel.loadItems()
            false
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupRecyclerView() {
        itemAdapter = ItemAdapter(emptyList()) { item ->
            val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(item)
            findNavController().navigate(action)
        }
        binding.itemRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.itemRecyclerview.adapter = itemAdapter
    }

    private fun observeViewModel() {
        itemViewModel.itemList.observe(viewLifecycleOwner) { itemList ->
            itemAdapter.updateData(itemList)
        }

        itemViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.itemRecyclerview.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
