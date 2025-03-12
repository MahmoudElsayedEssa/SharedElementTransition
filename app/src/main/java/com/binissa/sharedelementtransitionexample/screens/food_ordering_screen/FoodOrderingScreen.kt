package com.binissa.sharedelementtransitionexample.screens.food_ordering_screen

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.binissa.sharedelementtransitionexample.screens.food_ordering_screen.components.FoodItemCard
import com.binissa.sharedelementtransitionexample.screens.food_ordering_screen.components.FoodItemDetailView
import com.binissa.sharedelementtransitionexample.screens.food_ordering_screen.components.FoodItemListItem
import com.binissa.sharedelementtransitionexample.screens.food_ordering_screen.components.TopAppBarForMainView
import com.binissa.sharedelementtransitionexample.screens.home.FoodItem
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FoodOrderingScreen(viewModel: FoodOrderingViewModel = hiltViewModel()) {
    // Observe the full UI state from the view model
    val state by viewModel.state.collectAsState()
    val hazeState = remember { HazeState() }

    SharedTransitionLayout {
        AnimatedContent(
            targetState = state.selectedItem,
            transitionSpec = {
                fadeIn(tween(300)) togetherWith fadeOut(tween(300)) using SizeTransform { _, _ ->
                    spring(stiffness = 300f, dampingRatio = 0.8f)
                }
            },
            label = "MainContentTransition"
        ) { selectedItem ->
            // The outer AnimatedContent scope will be used for detail transitions.
            val outerScope = this@AnimatedContent

            if (selectedItem == null) {
                // List view: the screen state is in list mode when no item is selected.
                FoodListView(
                    foodItems = state.foodItems,
                    isGridView = state.isGridView,
                    onToggleView = { viewModel.toggleGridView() },
                    onItemClick = { viewModel.selectItem(it) },
                    onCartClick = { viewModel.toggleCartStatus(it) },
                    cartItemCount = state.cartItemCount,
                    hazeState = hazeState,
                    selectedItem = state.selectedItem,
                    animatedVisibilityScope = outerScope,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            } else {
                // Detail view
                FoodItemDetailView(
                    foodItem = selectedItem,
                    onBackClick = { viewModel.returnToList() },
                    onUpdateQuantity = { item, quantity ->
                        viewModel.updateQuantity(
                            item,
                            quantity
                        )
                    },
                    onAddToCart = {  },
                    animatedVisibilityScope = outerScope,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FoodListView(
    foodItems: List<FoodItem>,
    isGridView: Boolean,
    onToggleView: () -> Unit,
    onItemClick: (FoodItem) -> Unit,
    onCartClick: (FoodItem) -> Unit,
    cartItemCount: Int,
    hazeState: HazeState,
    selectedItem: FoodItem?, // Will be null in list mode
    animatedVisibilityScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope
) {
    with(animatedVisibilityScope) {
        with(sharedTransitionScope) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF9F9F9))
            ) {
                AnimatedContent(
                    targetState = isGridView,
                    transitionSpec = {
                        fadeIn(tween(300)) + slideInVertically { it } togetherWith
                                fadeOut(tween(300)) + slideOutVertically { -it }
                    },
                    label = "GridListTransition"
                ) { gridView ->
                    if (gridView) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .hazeSource(state = hazeState),
                            contentPadding = PaddingValues(8.dp, top = 80.dp)
                        ) {
                            items(foodItems, key = { it.id }) { item ->
                                Log.d("TAG", "FoodListView:" +
                                        "selectedItem:${selectedItem?.id} " +
                                        "item.id:${item.id}")
                                val visibilityScope =
                                    if (selectedItem?.id == item.id) animatedVisibilityScope else this@AnimatedContent
                                FoodItemListItem(
                                    foodItem = item,
                                    onItemClick = { onItemClick(item) },
                                    onCartClick = { onCartClick(item) },
                                    animatedVisibilityScope = visibilityScope,
                                    sharedTransitionScope = sharedTransitionScope
                                )
                            }
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxSize()
                                .hazeSource(state = hazeState),
                            contentPadding = PaddingValues(8.dp, top = 80.dp)
                        ) {
                            items(foodItems, key = { it.id }) { item ->
                                val visibilityScope =
                                    if (selectedItem?.id == item.id) animatedVisibilityScope else this@AnimatedContent
                                FoodItemCard(
                                    foodItem = item,
                                    onItemClick = { onItemClick(item) },
                                    onCartClick = { onCartClick(item) },
                                    animatedVisibilityScope = visibilityScope,
                                    sharedTransitionScope = sharedTransitionScope
                                )
                            }
                        }
                    }
                }

                // Top App Bar remains outside the grid/list AnimatedContent.
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(tween(300)) + slideInVertically { -it },
                    exit = fadeOut(tween(300)) + slideOutVertically { -it },
                    modifier = Modifier
                        .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 2f)
                        .animateEnterExit()
                ) {
                    TopAppBarForMainView(
                        isGridView = isGridView,
                        onToggleView = onToggleView,
                        cartItemCount = cartItemCount,
                        hazeState = hazeState,
                        animatedVisibilityScope = animatedVisibilityScope,
                        sharedTransitionScope = sharedTransitionScope
                    )
                }
            }
        }
    }
}