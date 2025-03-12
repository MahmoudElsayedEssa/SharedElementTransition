package com.binissa.sharedelementtransitionexample.screens.food_ordering_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun NutritionInfoItem(
    value: String,
    unit: String,
    name: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Text(
            text = unit,
            fontSize = 12.sp,
            color = Color.Gray
        )

        Text(
            text = name,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
