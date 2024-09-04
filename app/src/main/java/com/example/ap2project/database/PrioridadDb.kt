package com.example.ap2project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ap2project.dao.PrioridadDao
import com.example.ap2project.entities.PrioridadEntity

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