package com.example.ap2project.Data.dao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "Tickets")
class TicketEntity (
    @PrimaryKey
    val ticketId: Int? = null,
    val prioridadId: Int? = null,
    val date: Date? = null,
    val cliente: String? = null,
    val asunto: String? = null,
    val descripcion: String? = null
)