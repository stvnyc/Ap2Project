package com.example.ap2project.presentation.navigation.prioridad

import com.example.ap2project.Data.remote.dto.PrioridadDto

data class UiState(
    val prioridadId: Int? = null,
    val descripcion: String = "",
    val diasCompromiso: Int? = 0,
    val message: String? = null,
    val prioridades: List<PrioridadDto> = emptyList()
)

fun UiState.toEntity() = PrioridadDto(
    prioridadId = prioridadId,
    descripcion = descripcion,
    diasCompromiso = diasCompromiso ?: 0
)