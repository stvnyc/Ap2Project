package com.example.ap2project.Data.repository

import com.example.ap2project.Data.dao.dao.PrioridadDao
import com.example.ap2project.Data.dao.entities.PrioridadEntity
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
    private val prioridadDao: PrioridadDao
) {
    suspend fun save(prioridad: PrioridadEntity) =
        prioridadDao.save(prioridad)

    suspend fun getPrioridad(id: Int) =
        prioridadDao.find(id)

    suspend fun delete(prioridad: PrioridadEntity) =
        prioridadDao.delete(prioridad)

    fun getPrioridades() =
        prioridadDao.getall()
}