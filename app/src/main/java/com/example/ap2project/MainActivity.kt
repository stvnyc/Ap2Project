package com.example.ap2project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.ap2project.database.PrioridadDb
import com.example.ap2project.presentation.navigation.PrioridadNavHost
import com.example.ap2project.ui.theme.Ap2ProjectTheme

class MainActivity : ComponentActivity() {
    private lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            Ap2ProjectTheme {
                val navHost = rememberNavController()
                PrioridadNavHost(navHost,prioridadDb)
            }
        }
    }
}