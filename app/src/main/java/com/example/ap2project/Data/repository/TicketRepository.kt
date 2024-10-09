package com.example.ap2project.Data.repository

import com.example.ap2project.Data.remote.dto.TicketDto
import com.example.ap2project.Data.remote.ticketApi
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val  ticketApi: ticketApi
) {
    suspend fun saveTicket(ticket: TicketDto) =
        ticketApi.saveTicket(ticket)

    suspend fun getTicket(id: Int) =
        ticketApi.getTicket(id)

    suspend fun deleteTicket(id: Int) =
        ticketApi.deleteTicket(id)

    suspend fun getTickets() =
        ticketApi.getAllTicket()
}