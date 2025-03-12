package com.binissa.sharedelementtransitionexample.screens.food_ordering_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.binissa.sharedelementtransitionexample.ui.theme.Grid2x2
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInputScale
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import kotlinx.coroutines.delay
import kotlin.math.sin

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalHazeApi::class
)
@Composable
fun TopAppBarForMainView(
    isGridView: Boolean,
    onToggleView: () -> Unit,
    cartItemCount: Int,
    hazeState: HazeState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    // Track previous cart count for animation purposes
    var previousCartCount by remember { mutableIntStateOf(0) }
    val cartCountChanged = cartItemCount != previousCartCount

    // Animate badge when cart count changes
    val badgeTransition = updateTransition(targetState = cartCountChanged, label = "BadgeAnimation")
    val badgeScale by badgeTransition.animateFloat(
        transitionSpec = {
            if (targetState && cartItemCount > previousCartCount) {
                // Bouncy animation when adding items
                spring(stiffness = 400f, dampingRatio = 0.4f)
            } else {
                tween(300, easing = FastOutSlowInEasing)
            }
        }, label = "BadgeScale"
    ) { if (it) 1.3f else 1f }

    // Update previous count after animation completes
    LaunchedEffect(cartItemCount) {
        delay(300)
        previousCartCount = cartItemCount
    }

    // View mode transition for grid/list toggle
    val viewModeTransition =
        updateTransition(targetState = isGridView, label = "ViewModeTransition")

    // Grid/List toggle animation effects
    val iconRotation by viewModeTransition.animateFloat(
        transitionSpec = { tween(300, easing = FastOutSlowInEasing) }, label = "IconRotation"
    ) { if (it) 0f else 180f }

    val iconColor by viewModeTransition.animateColor(
        transitionSpec = { tween(300) }, label = "IconColor"
    ) { if (it) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary }

    with(sharedTransitionScope) {
        with(animatedVisibilityScope) {
            TopAppBar(title = {
                Text(
                    "Food Delivery",
                    modifier = Modifier.animateEnterExit(enter = fadeIn() + slideInVertically { -50 },
                        exit = fadeOut() + slideOutVertically { -50 })
                )
            },
                navigationIcon = {
                    IconButton(
                        onClick = { /* Open drawer */ }, modifier = Modifier.animateEnterExit(
                            enter = fadeIn() + scaleIn(initialScale = 0.8f),
                            exit = fadeOut() + scaleOut(targetScale = 0.8f)
                        )
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    // Cart icon with animated badge
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.animateEnterExit(
                            enter = fadeIn() + scaleIn(initialScale = 0.8f),
                            exit = fadeOut() + scaleOut(targetScale = 0.8f)
                        )
                    ) {
                        BadgedBox(badge = {
                            if (cartItemCount > 0) {
                                Badge(
                                    modifier = Modifier.scale(badgeScale)
                                ) {
                                    Text(text = cartItemCount.toString(),
                                        modifier = Modifier.graphicsLayer {
                                            // Add slight wobble effect on change
                                            if (cartCountChanged) {
                                                rotationZ =
                                                    sin(System.currentTimeMillis() % 1000 / 100f) * 5f
                                            }
                                        })
                                }
                            }
                        }) {
                            IconButton(
                                onClick = { /* Open cart */ },
                            ) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = "Shopping Cart",
                                    tint = if (cartItemCount > 0) MaterialTheme.colorScheme.primary
                                    else LocalContentColor.current
                                )
                            }
                        }
                    }

                    // Enhanced Grid/List toggle with rotation and icon swap animation
                    IconButton(
                        onClick = onToggleView, modifier = Modifier.animateEnterExit(
                            enter = fadeIn() + scaleIn(initialScale = 0.8f),
                            exit = fadeOut() + scaleOut(targetScale = 0.8f)
                        )
                    ) {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer {
                                    rotationY = iconRotation
                                    cameraDistance = 12 * density
                                }) {
                            AnimatedContent(
                                targetState = isGridView, transitionSpec = {
                                    (scaleIn(
                                        initialScale = 0.0f, animationSpec = tween(300)
                                    ) + fadeIn(animationSpec = tween(200))) togetherWith (scaleOut(
                                        targetScale = 0.0f, animationSpec = tween(300)
                                    ) + fadeOut(animationSpec = tween(200)))
                                }, label = "GridListIconTransition"
                            ) { targetState ->
                                Icon(
                                    imageVector = if (targetState) Icons.AutoMirrored.Filled.List
                                    else Grid2x2,
                                    contentDescription = if (targetState) "Switch to List"
                                    else "Switch to Grid",
                                    tint = iconColor,
                                    modifier = Modifier.scale(1.2f)
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 2f)
                    .animateEnterExit(fadeIn(), fadeOut())
                    .hazeEffect(state = hazeState) {
                        backgroundColor = Color.Transparent
                        inputScale = HazeInputScale.Auto
                        progressive =
                            HazeProgressive.verticalGradient(startIntensity = 2f, endIntensity = 0f)

                    })
        }
    }
}

