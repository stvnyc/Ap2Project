package com.example.ap2project.Data.remote

import com.example.ap2project.Data.remote.dto.ClienteDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface clienteApi {
    @GET("api/Clientes/{id}")
    suspend fun getCliente(@Path("id") id: Int): ClienteDto

    @GET("api/Clientes")
    suspend fun getAllCliente(): List<ClienteDto>

    @POST("api/Clientes")
    suspend fun saveCliente(@Body clienteDto: ClienteDto?): ClienteDto

    @DELETE("api/Clientes/{id}")
    suspend fun deleteCliente(@Path("id") id: Int)
}