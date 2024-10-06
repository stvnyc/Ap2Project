package com.example.ap2project.presentation.navigation.ticket

import com.example.ap2project.Data.dao.entities.PrioridadEntity
import com.example.ap2project.Data.dao.entities.TicketEntity
import java.sql.Date

data class UiState(
    val ticketId: Int? = null,
    val prioridadId: Int? = null,
    val date: Date? = null,
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val message: String? = null,
    val tickets: List<TicketEntity> = emptyList()
)

fun UiState.toEntity() = TicketEntity(
    ticketId = ticketId,
    prioridadId = prioridadId,
    date = date,
    cliente = cliente,
    asunto = asunto,
    descripcion = descripcion
)