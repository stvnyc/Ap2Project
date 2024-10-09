package com.example.ap2project.Data.repository

import com.example.ap2project.Data.remote.dto.PrioridadDto
import com.example.ap2project.Data.remote.prioridadApi
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
    private val prioridadApi: prioridadApi
) {
    suspend fun savePrioridad(prioridad: PrioridadDto) =
        prioridadApi.savePrioridad(prioridad)

    suspend fun getPrioridad(id: Int) =
        prioridadApi.getPrioridad(id)

    suspend fun deletePrioridad(id: Int) =
        prioridadApi.deletePrioridad(id)

    suspend fun getPrioridades() =
        prioridadApi.getAllPrioridad()
}