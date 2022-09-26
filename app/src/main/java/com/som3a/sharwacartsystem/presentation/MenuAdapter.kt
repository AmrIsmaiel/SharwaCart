package com.som3a.sharwacartsystem.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.som3a.sharwacartsystem.R
import com.som3a.sharwacartsystem.data.model.MenuCategory
import com.som3a.sharwacartsystem.databinding.ItemMenuBinding

class MenuAdapter(
    private val addToCartClicked: ((menuIte: MenuCategory) -> Unit)? = null,
    private val removeFromCartClicked: ((menuIte: MenuCategory) -> Unit)? = null,
) : ListAdapter<MenuCategory, MenuAdapter.MenuItemViewHolder>(MenuItemDiffUtil()) {

    inner class MenuItemViewHolder(
        private val binding: ItemMenuBinding,
        private val addToCartClicked: ((menuIte: MenuCategory) -> Unit)? = null,
        private val removeFromCartClicked: ((menuIte: MenuCategory) -> Unit)? = null,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItem: MenuCategory) {
            with(binding) {
                title.text = menuItem.name
                description.text = menuItem.decscriptionText
                price.text = String.format(
                    root.context.getString(R.string.price),
                    menuItem.price.toInt(),
                    menuItem.currency
                )
                addToCartButton.isEnabled = !menuItem.isAddedToCart
                removeFromCartButton.isEnabled = menuItem.isAddedToCart
                addToCartButton.setOnClickListener { addToCartClicked?.invoke(menuItem) }
                removeFromCartButton.setOnClickListener { removeFromCartClicked?.invoke(menuItem) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val layout = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuItemViewHolder(layout, addToCartClicked, removeFromCartClicked)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MenuItemDiffUtil : DiffUtil.ItemCallback<MenuCategory>() {
    override fun areItemsTheSame(oldItem: MenuCategory, newItem: MenuCategory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MenuCategory, newItem: MenuCategory): Boolean {
        return oldItem == newItem
    }
}