package com.example.ap2project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.ap2project.database.PrioridadDb
import com.example.ap2project.presentation.navigation.prioridad.PrioridadListScreen
import com.example.ap2project.presentation.navigation.prioridad.PrioridadScreen

@Composable
fun PrioridadNavHost(
    navHostController: NavHostController,
    prioridadDb: PrioridadDb
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val prioridadList by prioridadDb.prioridadDao().getall()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )
    NavHost(
        navController = navHostController,
        startDestination = Screen.PrioridadList
    ) {
        composable<Screen.PrioridadList> {
            PrioridadListScreen(
                prioridadList = prioridadList,
                createPrioridad = {navHostController.navigate(Screen.Prioridad(0))},
                goToPrioridadScreen = {navHostController.navigate(Screen.Prioridad(it))}
            )
        }
        composable<Screen.Prioridad> {
            val prioridadId = it.toRoute<Screen.Prioridad>().prioridadId
            PrioridadScreen(
                onGoToPrioridadListScreen = { navHostController.navigateUp() },
                prioridadDb,
                prioridadId
            )
        }
    }
}