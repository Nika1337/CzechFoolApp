package com.example.czechfoolapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.czechfoolapp.core.database.dao.GameDao
import com.example.czechfoolapp.core.database.dao.PlayerDao
import com.example.czechfoolapp.core.database.model.Converters
import com.example.czechfoolapp.core.database.model.GameEntity
import com.example.czechfoolapp.core.database.model.PlayerEntity

@Database(
    entities = [
        PlayerEntity::class,
        GameEntity::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(value = [Converters::class])
abstract class CzechFoolGameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun playerDao(): PlayerDao
}



