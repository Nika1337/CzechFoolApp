package com.example.czechfoolapp.core.database.dao

import android.content.Context
import androidx.room.Room
import com.example.czechfoolapp.core.database.CzechFoolGameDatabase
import com.example.czechfoolapp.core.database.model.GameEntity
import com.example.czechfoolapp.core.database.model.GameWithPlayers
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class GameDaoTest {
    private lateinit var gameDao: GameDao
    private lateinit var czechFoolGameDatabase: CzechFoolGameDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        czechFoolGameDatabase = Room.inMemoryDatabaseBuilder(context, CzechFoolGameDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        gameDao = czechFoolGameDatabase.gameDao()
    }

    @After
    fun closeDb() {
        czechFoolGameDatabase.close()
    }

    private val gameEntity1 =
        GameEntity(
            gameId = 1,
            losingScore = 200,
            date = LocalDateTime.now(),
        )

    private val gameEntity2 =
        GameEntity(
            gameId = 2,
            losingScore = 300,
            date = LocalDateTime.now(),
        )

    private suspend fun addOneGameToDb(gameEntity: GameEntity) {
        gameDao.insert(gameEntity)
    }

    private suspend fun addTwoGamesToDb() {
        gameDao.insert(gameEntity1)
        gameDao.insert(gameEntity2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsGameIntoDb() = runTest {
        addOneGameToDb(gameEntity1)
        val allGames = gameDao.getAllGames().first()
        assertEquals(allGames[0].gameEntity, gameEntity1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsertDuplicateGameId_replacesGame() = runTest{
        addOneGameToDb(gameEntity1)
        assertEquals(gameEntity1, gameDao.getGame(gameEntity1.gameId).first()!!.gameEntity)

        val copyGame = gameEntity1.copy(losingScore = gameEntity1.losingScore + 200)
        addOneGameToDb(copyGame)
        assertEquals(copyGame, gameDao.getGame(gameEntity1.gameId).first()!!.gameEntity)
    }


    @Test
    @Throws(Exception::class)
    fun daoGetGameNotInTable_returnsNull() = runTest {
        val game = gameDao.getGame(0).first()
        assertNull(game)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetGame_returnsCorrectGame() = runTest{
        addOneGameToDb(gameEntity1)
        val expectedValue = gameEntity1
        val actualValue = gameDao.getGame(gameEntity1.gameId).first()!!.gameEntity
        assertEquals(expectedValue, actualValue)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllGameOnEmptyTable_returnsEmptyList() = runTest {
        val games = gameDao.getAllGames().first()
        assertTrue(games.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllGames_returnsAllGamesFromDb() = runTest{
        addTwoGamesToDb()
        val allItems = gameDao.getAllGames().first()
        assertEquals(allItems[0].gameEntity, gameEntity2)
        assertEquals(allItems[1].gameEntity, gameEntity1)
    }


    @Test
    @Throws(Exception::class)
    fun daoDeleteGames_deletesAllGamesFromDb() = runTest {
        addTwoGamesToDb()
        gameDao.delete(gameEntity1)
        gameDao.delete(gameEntity2)

        val allGames = gameDao.getAllGames().first()
        assertTrue(allGames.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteGame_deletesGameFromDb() = runTest {
        addTwoGamesToDb()
        gameDao.delete(gameEntity1)

        val expectedValue = listOf(GameWithPlayers(gameEntity = gameEntity2, playerEntities = emptyList()))
        val actualValue = gameDao.getAllGames().first()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteGameNotInDatabase_doesNothing() = runTest {
        gameDao.delete(gameEntity1)
        assertTrue(gameDao.getAllGames().first().isEmpty())
    }

    @Test
    fun daoGetMaxGameId_returnsMaxGameID() = runTest {
        addTwoGamesToDb()

        val expectedValue = 2
        val actualValue = gameDao.getMaxGameID()

        assertEquals(expectedValue, actualValue)
    }

}












