package com.example.ap2project.Data.remote.dto

import java.util.Date

data class TicketDto (
    val ticketId: Int?,
    val date: Date = Date(),
    val clienteId: Int?,
    val sistemaId: Int?,
    val prioridadId: Int?,
    val solicitadoPor: String,
    val asunto: String,
    val descripcion: String,
)
