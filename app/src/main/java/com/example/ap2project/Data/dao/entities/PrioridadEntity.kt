package com.example.ap2project.Data.dao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prioridades")
data class PrioridadEntity(
    @PrimaryKey
    val prioridadId: Int? = null,
    val descripcion: String = "",
    val diasCompromiso: Int? = null
)