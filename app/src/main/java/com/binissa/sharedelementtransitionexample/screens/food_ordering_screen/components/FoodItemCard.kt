package com.binissa.sharedelementtransitionexample.screens.food_ordering_screen.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binissa.sharedelementtransitionexample.screens.home.FoodItem

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FoodItemCard(
    foodItem: FoodItem,
    onItemClick: () -> Unit,
    onCartClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    with(sharedTransitionScope) {
        ElevatedCard(
            modifier = Modifier
                .padding(8.dp)
                .sharedBounds(
                    rememberSharedContentState(key = "container-${foodItem.id}"),
                    animatedVisibilityScope,
                    clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(16.dp))
                )
                .clickable(onClick = onItemClick),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                Box {
                    Image(
                        painter = painterResource(id = foodItem.imageRes),
                        contentDescription = foodItem.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
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
                                .padding(8.dp)
                                .size(24.dp)
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
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    // Preparation time badge
                    Surface(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.BottomStart)
                            .sharedElement(
                                state = rememberSharedContentState(key = "prep-time-${foodItem.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            ),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0x99000000)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${foodItem.preparationTime} min",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Column(Modifier.padding(12.dp)) {
                    Text(
                        text = foodItem.name,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState(key = "title-${foodItem.id}"),
                            animatedVisibilityScope
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = foodItem.rating.toString(),
                                fontSize = 12.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }

                        Text(
                            text = foodItem.price,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "price-${foodItem.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        )
                    }

                    Text(
                        text = foodItem.description,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(top = 4.dp, bottom = 8.dp)
                            .sharedElement(
                                state = rememberSharedContentState(key = "desc-preview-${foodItem.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                    )

                    SelectionButton(
                        isSelected = foodItem.isInCart,
                        onClick = onCartClick,
                        modifier = Modifier
                            .align(Alignment.End)
                            .sharedElement(
                                state = rememberSharedContentState(key = "cart-button-${foodItem.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                    )
                }
            }
        }
    }
}