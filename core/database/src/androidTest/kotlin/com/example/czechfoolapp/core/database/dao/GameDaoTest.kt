package com.example.czechfoolapp.core.database.dao

import android.content.Context
import androidx.room.Room
import com.example.czechfoolapp.core.database.CzechFoolGameDatabase
import com.example.czechfoolapp.core.database.model.GameEntity
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
    private val gameEntity1 = FakeDataSource.game1
    private val gameEntity2 = FakeDataSource.game2

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

    private suspend fun addOneGameToDb(gameEntity: GameEntity) = gameDao.insert(gameEntity)

    private suspend fun addTwoGamesToDb() {
        gameDao.insert(gameEntity1)
        gameDao.insert(gameEntity2)
    }

    private suspend fun getAllGames(): List<GameEntity> =
        gameDao.getAllGames().first().map {
            it.gameEntity
        }


    private suspend fun getGameWithId(id: Int) = gameDao.getGame(id).first()?.gameEntity




    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsGameIntoDb() = runTest {
        val testGame = gameEntity1
        addOneGameToDb(testGame)
        val actual = getGameWithId(gameEntity1.gameId)
        val expected = testGame
        assertEquals(expected, actual)
    }

    @Test
    @Throws
    fun daoInsertWithGameIdNotSet_returnsCorrectGameId() = runTest {
        val testGame = GameEntity(
            losingScore = 200,
            date = LocalDateTime.now()
        )
        val gameId = addOneGameToDb(testGame)
        val expected = 1L
        val actual = gameId
        assertEquals(expected, actual)
    }

    @Test
    @Throws
    fun daoInsertWithGameIdSet_returnsCorrectGameId() = runTest {
        val testGame = GameEntity(
            gameId = 167,
            losingScore = 200,
            date = LocalDateTime.now()
        )
        val gameId = addOneGameToDb(testGame).toInt()
        val expected = testGame.gameId
        val actual = gameId
        assertEquals(expected, actual)
    }


    @Test
    @Throws(Exception::class)
    fun daoInsertDuplicateGameId_replacesGame() = runTest{
        val testGame = gameEntity1
        addOneGameToDb(testGame)

        val copyGame = testGame.copy(losingScore = testGame.losingScore + 200)
        addOneGameToDb(copyGame)
        val expected = copyGame
        val actual = getGameWithId(testGame.gameId)
        assertEquals(expected, actual)
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
        val expected = gameEntity1
        val actual = gameDao.getGame(gameEntity1.gameId).first()!!.gameEntity
        assertEquals(expected, actual)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllGameOnEmptyTable_returnsEmptyList() = runTest {
        val games = getAllGames()
        assertTrue(games.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllGames_returnsAllGamesFromDb() = runTest{
        addTwoGamesToDb()
        val actual = getAllGames()
        val expected = listOf(gameEntity1, gameEntity2)
        assertEquals(expected, actual)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteGames_deletesAllGamesFromDb() = runTest {
        addTwoGamesToDb()
        gameDao.delete(gameEntity1)
        gameDao.delete(gameEntity2)

        val allGames = getAllGames()
        assertTrue(allGames.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteGame_deletesGameFromDb() = runTest {
        addTwoGamesToDb()
        gameDao.delete(gameEntity1)

        val expected = listOf(gameEntity2)
        val actual = getAllGames()
        assertEquals(expected, actual)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteGameNotInDatabase_doesNothing() = runTest {
        gameDao.delete(gameEntity1)
        assertTrue(getAllGames().isEmpty())
    }
}












