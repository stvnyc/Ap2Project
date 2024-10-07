package com.example.ap2project.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()
    @Serializable
    data object PrioridadList : Screen()
    @Serializable
    data class Prioridad(val prioridadId: Int) : Screen()
    @Serializable
    data object TicketListScreen : Screen()
    @Serializable
    data class TicketScreen(val ticketId: Int) : Screen()
}