package com.example.ap2project.presentation.navigation.ticket

import com.example.ap2project.Data.remote.dto.ClienteDto
import com.example.ap2project.Data.remote.dto.PrioridadDto
import com.example.ap2project.Data.remote.dto.SistemaDto
import com.example.ap2project.Data.remote.dto.TicketDto
import java.util.Date

data class UiState(
    val ticketId: Int? = null,
    val date: Date = Date(),
    val clienteId: Int? = null,
    val sistemaId: Int? = null,
    val prioridadId: Int? = null,
    val solicitadoPor: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val message: String? = null,
    val errorFecha: String? = null,
    val tickets: List<TicketDto> = emptyList(),
    val clientes: List<ClienteDto> = emptyList(),
    val sistemas: List<SistemaDto> = emptyList(),
    val prioridades: List<PrioridadDto> = emptyList()
)

fun UiState.toEntity() = TicketDto(
    ticketId = ticketId,
    date = date,
    clienteId = clienteId,
    sistemaId = sistemaId,
    prioridadId = prioridadId,
    solicitadoPor = solicitadoPor,
    asunto = asunto,
    descripcion = descripcion
)