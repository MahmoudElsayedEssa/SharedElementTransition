package com.binissa.sharedelementtransitionexample.screens.food_ordering_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SelectionButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    iconSize: Dp = 20.dp
) {
    val transition = updateTransition(targetState = isSelected, label = "SelectionTransition")

    val scale by transition.animateFloat(
        transitionSpec = { tween(250, easing = FastOutSlowInEasing) },
        label = "ScaleAnimation"
    ) { if (it) 1.1f else 1f }

    val backgroundColor by transition.animateColor(
        transitionSpec = { tween(300, easing = FastOutSlowInEasing) },
        label = "ColorAnimation"
    ) { if (it) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.3f) }

    Box(
        modifier = modifier
            .size(size)
            .scale(scale)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isSelected,
            transitionSpec = {
                (scaleIn(initialScale = 0.1f, animationSpec = tween(200)) +
                        fadeIn(animationSpec = tween(200))) togetherWith
                        (scaleOut(targetScale = 2f, animationSpec = tween(200)) +
                                fadeOut(animationSpec = tween(200)))
            },
            label = "IconAnimation"
        ) { targetState ->
            Icon(
                imageVector = if (targetState) Icons.Default.Check else Icons.Default.Add,
                contentDescription = if (targetState) "Selected" else "Add",
                tint = if (targetState) Color.White else Color.Black,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}