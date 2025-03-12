package com.binissa.sharedelementtransitionexample.screens.food_ordering_screen

import com.binissa.sharedelementtransitionexample.screens.home.FoodItem
import com.binissa.sharedelementtransitionexample.screens.home.sampleFoodItems

data class FoodOrderingViewState(
    val foodItems: List<FoodItem> = sampleFoodItems,
    val isGridView: Boolean = true,
    val cartItemCount: Int = 0,
    val selectedItem: FoodItem? = null
)
