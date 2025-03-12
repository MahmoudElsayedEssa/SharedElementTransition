package com.binissa.sharedelementtransitionexample.screens.food_ordering_screen.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binissa.sharedelementtransitionexample.screens.home.FoodItem
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInputScale
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource

@OptIn(
    ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class,
    ExperimentalHazeApi::class
)
@Composable
fun FoodItemDetailView(
    foodItem: FoodItem,
    onBackClick: () -> Unit,
    onUpdateQuantity: (FoodItem, Int) -> Unit,
    onAddToCart: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    var quantity by remember { mutableIntStateOf(foodItem.quantity) }
    val scrollState = rememberScrollState()
    val hazeState = remember { HazeState() }

    with(sharedTransitionScope) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = foodItem.name,
                            modifier = Modifier.sharedElement(
                                rememberSharedContentState(key = "title-${foodItem.id}"),
                                animatedVisibilityScope
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Toggle favorite */ }) {
                            Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                    modifier = Modifier.hazeEffect(state = hazeState) {
                        backgroundColor = Color.Transparent
                        inputScale = HazeInputScale.Auto
                        progressive =
                            HazeProgressive.verticalGradient(startIntensity = 1f, endIntensity = 0f)

                    }

                )
            },
            bottomBar = {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Quantity:",
                                fontWeight = FontWeight.Medium
                            )

                            IconButton(
                                onClick = {
                                    if (quantity > 0) quantity--
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Text(
                                    text = "−",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            }

                            Text(
                                text = quantity.toString(),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            IconButton(
                                onClick = { quantity++ },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Text(
                                    text = "+",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            }
                        }

                        Button(
                            onClick = {
                                onUpdateQuantity(foodItem, quantity)
                                onAddToCart()
                            },
                            modifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "cart-button-${foodItem.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (foodItem.isInCart) "Update Cart" else "Add to Cart"
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(paddingValues)
                    .verticalScroll(scrollState)
                    .sharedBounds(
                        rememberSharedContentState(key = "container-${foodItem.id}"),
                        animatedVisibilityScope,
                        clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(16.dp))
                    )
                    .hazeSource(hazeState)
            ) {
                // Hero image with badges
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                        .aspectRatio(1f)
                ) {
                    Image(
                        painter = painterResource(id = foodItem.imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .sharedElement(
                                rememberSharedContentState(key = "image-${foodItem.id}"),
                                animatedVisibilityScope,
                                placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize
                            ),
                        contentScale = ContentScale.Crop
                    )

                    // Vegetarian badge
                    if (foodItem.isVegetarian) {
                        Surface(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(32.dp)
                                .align(Alignment.TopEnd)
                                .sharedElement(
                                    state = rememberSharedContentState(key = "veg-badge-${foodItem.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                ),
                            shape = CircleShape,
                            color = Color(0xFF388E3C)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "V",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }

                    // Preparation time badge
                    Surface(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.BottomStart)
                            .sharedElement(
                                state = rememberSharedContentState(key = "prep-time-${foodItem.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            ),
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0x99000000)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Prep time: ${foodItem.preparationTime} min",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Item details
                Column(Modifier.padding(16.dp)) {
                    // Header section with price and rating
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = foodItem.price,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "price-${foodItem.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "rating-${foodItem.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = foodItem.rating.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                            Text(
                                text = "(${foodItem.reviews.size} reviews)",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Description section
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = foodItem.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "desc-preview-${foodItem.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Nutritional info
                    Text(
                        text = "Nutritional Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        NutritionInfoItem(
                            value = foodItem.calories.toString(),
                            unit = "cal",
                            name = "Calories"
                        )

                        // Mock nutritional values
                        NutritionInfoItem(
                            value = "${(foodItem.calories * 0.045).toInt()}",
                            unit = "g",
                            name = "Protein"
                        )

                        NutritionInfoItem(
                            value = "${(foodItem.calories * 0.033).toInt()}",
                            unit = "g",
                            name = "Carbs"
                        )

                        NutritionInfoItem(
                            value = "${(foodItem.calories * 0.022).toInt()}",
                            unit = "g",
                            name = "Fat"
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Ingredients section
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    foodItem.ingredients.forEachIndexed { index, ingredient ->
                        Text(
                            text = "• $ingredient",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Reviews section
                    Text(
                        text = "Customer Reviews",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    foodItem.reviews.forEach { review ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = review.reviewerName,
                                        fontWeight = FontWeight.Medium
                                    )

                                    Text(
                                        text = review.date,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        tint = Color(0xFFFFC107),
                                        modifier = Modifier.size(16.dp)
                                    )

                                    Text(
                                        text = review.rating.toString(),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = review.comment,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    // Add some space at the bottom for the bottom bar
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
