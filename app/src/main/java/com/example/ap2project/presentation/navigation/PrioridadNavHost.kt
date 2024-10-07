package com.example.ap2project.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.ap2project.presentation.navigation.components.ModalDrawerSheet
import com.example.ap2project.presentation.navigation.prioridad.PrioridadListScreen
import com.example.ap2project.presentation.navigation.prioridad.PrioridadScreen
import com.example.ap2project.presentation.navigation.ticket.TicketListScreen
import com.example.ap2project.presentation.navigation.ticket.TicketScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadNavHost(
    navHostController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(coroutineScope, drawerState, navHostController)
        },
    ) { Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },

                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(Icons.Rounded.Menu, contentDescription = "Boton de menu")
                        }
                    }
                )
            }
        ) { innerpadding ->
            NavHost(
                navController = navHostController,
                startDestination = Screen.Home,
                modifier = Modifier.padding(innerpadding)
            ) {
                composable<Screen.Home> {
                    Text("Se encuentra en el inicio")
                }

                composable<Screen.PrioridadList> {
                    PrioridadListScreen(
                        createPrioridad = { navHostController.navigate(Screen.Prioridad(0)) },
                        goToPrioridadScreen = { navHostController.navigate(Screen.Prioridad(it)) }
                    )
                }

                composable<Screen.Prioridad> {
                    val prioridadId = it.toRoute<Screen.Prioridad>().prioridadId
                    PrioridadScreen(
                        onGoToPrioridadListScreen = { navHostController.navigateUp() },
                        prioridadId = prioridadId
                    )
                }
                composable<Screen.TicketListScreen>{
                    TicketListScreen(
                        createTicket = { navHostController.navigate(Screen.TicketScreen(0)) },
                        goToTicketScreen = { navHostController.navigate(Screen.TicketScreen(it)) }
                    )
                }
                composable<Screen.TicketScreen>{
                    val ticketId = it.toRoute<Screen.TicketScreen>().ticketId
                    TicketScreen(
                        onNavigateBack = { navHostController.navigateUp() },
                        ticketId = ticketId
                    )
                }
            }
        }
    }
}