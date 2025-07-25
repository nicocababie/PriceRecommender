package com.example.pricerecommender

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pricerecommender.ui.PriceRecommenderApp
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PriceRecommenderTheme {
                PriceRecommenderApp()
            }
        }
    }
}