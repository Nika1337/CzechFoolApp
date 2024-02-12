package com.example.czechfoolapp.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

class CurrentGameDataSerializer : Serializer<CurrentGameData> {
    override val defaultValue: CurrentGameData = CurrentGameData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CurrentGameData {
        try {
            return CurrentGameData.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: CurrentGameData, output: OutputStream) = t.writeTo(output)
}

val Context.currentGameDataStore: DataStore<CurrentGameData> by dataStore(
    fileName = "current_game_data.pb",
    serializer = CurrentGameDataSerializer()
)