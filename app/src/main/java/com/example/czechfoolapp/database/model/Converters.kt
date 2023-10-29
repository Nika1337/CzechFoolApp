package com.example.czechfoolapp.database.model

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.LocalDateTime.parse


class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }
}