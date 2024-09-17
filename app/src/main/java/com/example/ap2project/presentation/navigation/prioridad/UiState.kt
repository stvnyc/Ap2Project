package com.example.ap2project.presentation.navigation.prioridad

import android.os.Message
import com.example.ap2project.Data.dao.entities.PrioridadEntity

data class UiState(
    val prioridadId: Int? = null,
    val descripcion: String = "",
    val diasCompromiso: Int? = 0,
    val errorMessage: String? = null,
    val prioridades: List<PrioridadEntity> = emptyList()
)

fun UiState.toEntity() = PrioridadEntity(
    prioridadId = prioridadId,
    descripcion = descripcion,
    diasCompromiso = diasCompromiso ?: 0
)