package com.example.ap2project.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data object ClienteListScreen : Screen()
    @Serializable
    data class ClienteScreen(val clienteId: Int) : Screen()

    @Serializable
    data object PrioridadList : Screen()
    @Serializable
    data class Prioridad(val prioridadId: Int) : Screen()

    @Serializable
    data object SistemaListScreen : Screen()
    @Serializable
    data class SistemaScreen(val sistemaId: Int) : Screen()

    @Serializable
    data object TicketListScreen : Screen()
    @Serializable
    data class TicketScreen(val ticketId: Int) : Screen()
}