package com.example.ap2project.presentation.navigation.sistema

import com.example.ap2project.Data.remote.dto.SistemaDto

data class UiState(
    val sistemaId: Int? = null,
    val sistemaNombre: String = "",
    val message: String? = null,
    val sistemas: List<SistemaDto> = emptyList()
)

fun UiState.toEntity() = SistemaDto(
    sistemaId = sistemaId,
    sistemaNombre = sistemaNombre
)