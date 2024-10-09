package com.example.ap2project.Data.remote

import com.example.ap2project.Data.remote.dto.TicketDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ticketApi {
    @GET("api/Tickets/{id}")
    suspend fun getTicket(@Path("id") id: Int): TicketDto

    @GET("api/Tickets")
    suspend fun getAllTicket(): List<TicketDto>

    @POST("api/Tickets")
    suspend fun saveTicket(@Body ticketDto: TicketDto?): TicketDto

    @DELETE("api/Tickets/{id}")
    suspend fun deleteTicket(@Path("id") id: Int)
}