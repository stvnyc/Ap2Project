package com.example.ap2project.Data.repository

import com.example.ap2project.Data.remote.dto.SistemaDto
import com.example.ap2project.Data.remote.sistemaApi
import javax.inject.Inject

class SistemaRepository @Inject constructor(
    private val sistemaApi: sistemaApi
) {
    suspend fun saveSistema(sistema: SistemaDto) =
        sistemaApi.saveSistema(sistema)

    suspend fun getSistema(id: Int) =
        sistemaApi.getSistema(id)

    suspend fun deleteSistema(id: Int) =
        sistemaApi.deleteSistema(id)

    suspend fun getSistemas() =
        sistemaApi.getAllSistema()
}