package com.example.czechfoolapp.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import java.io.IOException

class DefaultCurrentGameDataSource(
    private val currentGameData: DataStore<CurrentGameData>
) : CurrentGameDataSource {
    override fun getCurrentGameDataFlow()= currentGameData.data
    override suspend fun setCurrentGameID(id: Int) {
        try {
            currentGameData.updateData {
                it.toBuilder().setId(id).build()
            }
        } catch (exception: IOException) {
            Log.e(this.javaClass.name, "failed to update current game id", exception)
        }
    }
}