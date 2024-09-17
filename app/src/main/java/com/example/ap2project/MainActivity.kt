package com.example.ap2project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.ap2project.Data.dao.database.PrioridadDb
import com.example.ap2project.presentation.navigation.PrioridadNavHost
import com.example.ap2project.ui.theme.Ap2ProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Ap2ProjectTheme {
                val navHost = rememberNavController()
                PrioridadNavHost(navHost)
            }
        }
    }
}