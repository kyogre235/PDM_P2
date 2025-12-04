package com.example.pokedex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemListItemBinding
import com.example.pokedex.model.Item

/*
* Adapter para la lista de items
*/
class ItemAdapter(
    private var itemList: List<Item>,
    private val onItemClicked: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    fun updateData(newItemList: List<Item>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked(item)
        }
    }

    override fun getItemCount() = itemList.size

    class ItemViewHolder(private val binding: ItemListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.itemName.text = item.name
            binding.itemCost.text = item.cost.toString()

            binding.itemImage.load(item.spriteUrl) {
                crossfade(true)
                placeholder(R.drawable.pokeball_placeholder)
                error(R.drawable.pokeball_placeholder)
            }
        }
    }
}
