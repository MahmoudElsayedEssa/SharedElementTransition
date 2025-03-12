package com.binissa.sharedelementtransitionexample.screens.food_ordering_screen

import androidx.lifecycle.ViewModel
import com.binissa.sharedelementtransitionexample.screens.home.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FoodOrderingViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(FoodOrderingViewState())
    val state: StateFlow<FoodOrderingViewState> = _state.asStateFlow()

    fun toggleGridView() {
        _state.update { current ->
            current.copy(isGridView = !current.isGridView)
        }
    }

    fun selectItem(item: FoodItem) {
        _state.update { current ->
            current.copy(selectedItem = item)
        }
    }

    fun returnToList() {
        _state.update { current ->
            current.copy(selectedItem = null)
        }
    }

    fun toggleCartStatus(item: FoodItem) {
        _state.update { current ->
            val updatedItems = current.foodItems.map {
                if (it.id == item.id) {
                    if (it.isInCart) {
                        // Removing from cart
                        it.copy(isInCart = false, quantity = 0)
                    } else {
                        // Adding to cart
                        it.copy(isInCart = true, quantity = 1)
                    }
                } else it
            }
            // Adjust the cart count based on the item toggle
            val currentItem = current.foodItems.find { it.id == item.id }
            val newCartCount = if (currentItem != null && currentItem.isInCart) {
                current.cartItemCount - currentItem.quantity
            } else {
                current.cartItemCount + 1
            }
            current.copy(foodItems = updatedItems, cartItemCount = newCartCount)
        }
    }

    fun updateQuantity(item: FoodItem, newQuantity: Int) {
        _state.update { current ->
            val updatedItems = current.foodItems.map {
                if (it.id == item.id) {
                    it.copy(quantity = newQuantity)
                } else it
            }
            // Update the cart count by calculating the difference for this item.
            val currentItem = current.foodItems.find { it.id == item.id }
            val difference = if (currentItem != null) newQuantity - currentItem.quantity else 0
            current.copy(
                foodItems = updatedItems,
                cartItemCount = current.cartItemCount + difference
            )
        }
    }
}

