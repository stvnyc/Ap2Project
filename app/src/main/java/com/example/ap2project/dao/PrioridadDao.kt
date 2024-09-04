package com.example.ap2project.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.ap2project.entities.PrioridadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrioridadDao{
    @Upsert()
    suspend fun save(prioridad: PrioridadEntity)

    @Query(
        """
            SELECT *
            FROM Prioridades
            WHERE prioridadId =:id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): PrioridadEntity?

    @Query("SELECT * FROM Prioridades WHERE descripcion LIKE :descripcion")
    suspend fun findByDescription(descripcion: String): PrioridadEntity?

    @Delete
    suspend fun delete(prioridad: PrioridadEntity)

    @Query("SELECT * FROM Prioridades")
    fun getall(): Flow<List<PrioridadEntity>>
}