package com.example.ap2project.Data.repository

import com.example.ap2project.Data.remote.clienteApi
import com.example.ap2project.Data.remote.dto.ClienteDto
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val clienteApi: clienteApi
) {
    suspend fun saveCliente(cliente: ClienteDto) =
        clienteApi.saveCliente(cliente)

    suspend fun getCliente(id: Int) =
        clienteApi.getCliente(id)

    suspend fun deleteCliente(id: Int) =
        clienteApi.deleteCliente(id)

    suspend fun getClientes() =
        clienteApi.getAllCliente()
}