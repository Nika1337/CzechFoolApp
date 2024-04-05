package com.example.czechfoolapp.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.czechfoolapp.core.database.CzechFoolGameDatabase
import com.example.czechfoolapp.core.database.model.GameEntity
import com.example.czechfoolapp.core.database.model.PlayerEntity
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class PlayerDaoTest {
    private lateinit var playerDao: PlayerDao
    private lateinit var czechFoolGameDatabase: CzechFoolGameDatabase
    private val defaultGameId = FakeDataSource.game1.gameId
    private val playerEntity1 = FakeDataSource.game1Players[0]
    private val playerEntity2 = FakeDataSource.game1Players[1]
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        czechFoolGameDatabase = Room.inMemoryDatabaseBuilder(context, CzechFoolGameDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        playerDao = czechFoolGameDatabase.playerDao()
        populateWithGame()
    }

    private fun populateWithGame() = runBlocking {
        val gameDao = czechFoolGameDatabase.gameDao()
        val gameEntity1 =
            GameEntity(
                gameId = defaultGameId,
                losingScore = 200,
                date = LocalDateTime.now(),
            )
        gameDao.insert(gameEntity1)
    }

    @After
    fun closeDb() {
        czechFoolGameDatabase.close()
    }

    private suspend fun addOnePlayerToDb(playerEntity: PlayerEntity = playerEntity1) {
        playerDao.insertAll(playerEntity)
    }

    private suspend fun addTwoPlayersToDb() {
        playerDao.insertAll(playerEntity1, playerEntity2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsPlayerIntoDb() = runTest {
        addOnePlayerToDb()
        val allPlayers = playerDao.getAllPlayersInGameSpecified(defaultGameId).first()
        assertEquals(playerEntity1, allPlayers[0])
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDb() = runTest {
        addTwoPlayersToDb()
        val allItems = playerDao.getAllPlayersInGameSpecified(defaultGameId).first()
        assertEquals(allItems[0], playerEntity1)
        assertEquals(allItems[1], playerEntity2)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetPlayer_returnsCorrectPlayerFromDb() = runTest {
        addTwoPlayersToDb()
        val allPlayers = playerDao.getAllPlayersInGameSpecified(defaultGameId).first()
        val expectedPlayerEntity1 = allPlayers[0]
        val expectedPlayerEntity2 = allPlayers[1]

        assertEquals(playerEntity1, expectedPlayerEntity1)
        assertEquals(playerEntity2, expectedPlayerEntity2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdatePlayer_updatesPlayerInDb() = runTest {
        val playerEntity1Copy = playerEntity1.copy(score = 67)

        addOnePlayerToDb()
        playerDao.update(playerEntity1Copy)

        val allPlayers = playerDao.getAllPlayersInGameSpecified(defaultGameId).first()
        assertEquals(playerEntity1Copy, allPlayers[0])
    }

    @Test
    @Throws(Exception::class)
    fun daoDeletePlayers_deletesAllPlayersFromDb() = runTest {
        addTwoPlayersToDb()
        playerDao.delete(playerEntity1)
        playerDao.delete(playerEntity2)

        val allPlayers = playerDao.getAllPlayersInGameSpecified(defaultGameId).first()
        assertTrue(allPlayers.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoAddPlayersWithSameNameToSameGame_throwsException() = runTest {
        val name = "Nika"
        addOnePlayerToDb(playerEntity1.copy(name = name))
        assertThrows(Exception::class.java) {
            runBlocking {
                addOnePlayerToDb(playerEntity2.copy(name = name))
            }
        }
    }
}


