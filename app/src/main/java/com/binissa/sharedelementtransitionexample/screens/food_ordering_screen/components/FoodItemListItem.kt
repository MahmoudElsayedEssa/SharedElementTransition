package com.binissa.sharedelementtransitionexample.screens.food_ordering_screen.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
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
fun FoodItemListItem(
    foodItem: FoodItem,
    onItemClick: () -> Unit,
    onCartClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        ElevatedCard(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "container-${foodItem.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(16.dp))
                ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onItemClick)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Image(
                        painter = painterResource(id = foodItem.imageRes),
                        contentDescription = foodItem.name,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .sharedElement(
                                state = rememberSharedContentState(key = "image-${foodItem.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize
                            ),
                        contentScale = ContentScale.Crop
                    )

                    if (foodItem.isVegetarian) {
                        Surface(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.TopEnd)
                                .offset(x = (-4).dp, y = 4.dp)
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
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = foodItem.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "title-${foodItem.id}"),
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
                        text = foodItem.description,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "desc-preview-${foodItem.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = foodItem.price,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "price-${foodItem.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    SelectionButton(
                        isSelected = foodItem.isInCart,
                        onClick = onCartClick,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = "cart-button-${foodItem.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    )
                }
            }
        }
    }
}
