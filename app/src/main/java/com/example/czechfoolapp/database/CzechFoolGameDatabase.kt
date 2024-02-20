package com.example.czechfoolapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.czechfoolapp.database.dao.GameDao
import com.example.czechfoolapp.database.dao.PlayerDao
import com.example.czechfoolapp.database.model.Converters
import com.example.czechfoolapp.database.model.GameEntity
import com.example.czechfoolapp.database.model.PlayerEntity

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



