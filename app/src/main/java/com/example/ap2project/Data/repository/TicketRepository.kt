package com.example.ap2project.Data.repository

import com.example.ap2project.Data.dao.dao.TicketDao
import com.example.ap2project.Data.dao.entities.TicketEntity
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val  ticketDao: TicketDao
) {
    suspend fun save (ticket: TicketEntity) = ticketDao.save(ticket)

    suspend fun getTicket (id: Int) = ticketDao.find(id)

    suspend fun delete(ticket: TicketEntity) = ticketDao.delete(ticket)

    fun getTickets() = ticketDao.getAll()
}