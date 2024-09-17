package com.example.ap2project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.ap2project.presentation.navigation.prioridad.PrioridadListScreen
import com.example.ap2project.presentation.navigation.prioridad.PrioridadScreen

@Composable
fun PrioridadNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.PrioridadList
    ) {
        composable<Screen.PrioridadList> {
            PrioridadListScreen(
                createPrioridad = {navHostController.navigate(Screen.Prioridad(0))},
                goToPrioridadScreen = {navHostController.navigate(Screen.Prioridad(it))}
            )
        }
        composable<Screen.Prioridad> {
            val prioridadId = it.toRoute<Screen.Prioridad>().prioridadId
            PrioridadScreen(
                onGoToPrioridadListScreen = { navHostController.navigateUp() },
                prioridadId = prioridadId
            )
        }
    }
}