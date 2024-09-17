package com.example.ap2project.Data.dao.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.ap2project.Data.dao.entities.PrioridadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrioridadDao{
    @Upsert
    suspend fun save(prioridad: PrioridadEntity)

    @Update
    suspend fun update(prioridad: PrioridadEntity)
    @Query(
        """
            SELECT *
            FROM Prioridades
            WHERE prioridadId =:id
            LIMIT 1
        """
    )

    suspend fun find(id: Int): PrioridadEntity?

    @Delete
    suspend fun delete(prioridad: PrioridadEntity)

    @Query("SELECT * FROM Prioridades")
    fun getall(): Flow<List<PrioridadEntity>>

    @Query("SELECT * FROM Prioridades WHERE LOWER(TRIM(descripcion)) = LOWER(TRIM(:descripcion)) LIMIT 1")
    suspend fun findByDescription(descripcion: String): PrioridadEntity?
}