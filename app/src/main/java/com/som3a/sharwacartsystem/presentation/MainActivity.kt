package com.som3a.sharwacartsystem.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import com.som3a.sharwacartsystem.data.model.MenuCategoryLocal
import com.som3a.sharwacartsystem.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val viewModel: CartViewModel by viewModels()

    lateinit var adapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        adapter = MenuAdapter(addToCartClicked = {
            viewModel.addItem(
                MenuCategoryLocal(
                    id = it.id,
                    name = it.name,
                    decscriptionText = it.decscriptionText,
                    icon = it.icon,
                    price = it.price,
                    currency = it.currency
                )
            )
        }, removeFromCartClicked = {
            viewModel.removeItemFromCart(
                MenuCategoryLocal(
                    id = it.id,
                    name = it.name,
                    decscriptionText = it.decscriptionText,
                    icon = it.icon,
                    price = it.price,
                    currency = it.currency
                )
            )
        })
        binding.recyclerView.adapter = adapter
        viewModel.getItemsUpdated()
        binding.clearCart.setOnClickListener {
            viewModel.clearAllItems()
        }
    }

    private fun initObservers() {
        viewModel.mGetItemsSuccess.observe(this) {
            var totalPrice = 0.0
            var totalCount = 0
            it.menuCategory.map { item ->
                if (item.isAddedToCart) {
                    totalPrice += item.price
                    totalCount += 1
                }
            }
            adapter.submitList(it.menuCategory)
            adapter.notifyDataSetChanged()
            binding.totalPrice.isVisible = totalPrice != 0.0
            binding.totalCount.isVisible = totalCount != 0
            binding.clearCart.isEnabled = totalCount != 0
            binding.totalPrice.text = buildSpannedString {
                append("Cart total price: ")
                bold {
                    append(totalPrice.toString())
                }
            }
            binding.totalCount.text = buildSpannedString {
                append("Cart total count: ")
                bold {
                    append(totalCount.toString())
                }
            }
        }
    }
}