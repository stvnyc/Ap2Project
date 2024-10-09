package com.example.ap2project.presentation.navigation.cliente

import com.example.ap2project.Data.remote.dto.ClienteDto

data class UiState(
    val clienteId: Int? = null,
    val nombre: String = "",
    val telefono: String = "",
    val celular: String = "",
    val rnc:  String = "",
    val email: String = "",
    val direccion: String = "",
    val message: String? = null,
    val clientes: List<ClienteDto> = emptyList()
)

fun UiState.toEntity() = ClienteDto(
    clienteId = clienteId,
    nombre = nombre,
    telefono = telefono,
    celular = celular,
    rnc = rnc,
    email = email,
    direccion = direccion
)