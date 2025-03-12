package com.binissa.sharedelementtransitionexample

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.binissa.sharedelementtransitionexample.screens.food_ordering_screen.FoodOrderingScreen
import com.binissa.sharedelementtransitionexample.ui.theme.SharedElementTransitionExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        setContent {
            SharedElementTransitionExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FoodOrderingScreen()
                }
            }
        }
    }
}
