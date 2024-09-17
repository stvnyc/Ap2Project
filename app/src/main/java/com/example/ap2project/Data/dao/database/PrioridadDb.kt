package com.example.ap2project.Data.dao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ap2project.Data.dao.dao.PrioridadDao
import com.example.ap2project.Data.dao.entities.PrioridadEntity

@Database(
    entities = [
        PrioridadEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class PrioridadDb : RoomDatabase(){
    abstract fun prioridadDao(): PrioridadDao
}