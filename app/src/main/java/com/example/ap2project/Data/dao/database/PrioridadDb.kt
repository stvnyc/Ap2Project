package com.example.ap2project.Data.dao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ap2project.Data.dao.converters.Converters
import com.example.ap2project.Data.dao.dao.PrioridadDao
import com.example.ap2project.Data.dao.dao.TicketDao
import com.example.ap2project.Data.dao.entities.PrioridadEntity
import com.example.ap2project.Data.dao.entities.TicketEntity

@Database(
    entities = [
        PrioridadEntity::class,
        TicketEntity::class
    ],
    version = 2,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class PrioridadDb : RoomDatabase(){
    abstract fun prioridadDao(): PrioridadDao

    abstract fun ticketDao(): TicketDao
}