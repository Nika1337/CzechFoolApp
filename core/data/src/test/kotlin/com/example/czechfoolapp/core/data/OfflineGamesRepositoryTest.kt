package com.example.czechfoolapp.core.data

import com.example.czechfoolapp.core.data.fake.FakeDataSource.games
import com.example.czechfoolapp.core.data.fake.FakeGameDao
import com.example.czechfoolapp.core.data.repository.GamesRepository
import com.example.czechfoolapp.core.data.repository.OfflineGamesRepository
import com.example.czechfoolapp.core.data.util.clearPlayers
import com.example.czechfoolapp.core.model.Game
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class OfflineGamesRepositoryTest {

    private lateinit var gamesRepository: GamesRepository
    @Before
    fun createRepository() {
        gamesRepository = OfflineGamesRepository(
            gameDao = FakeGameDao()
        )
    }

    private suspend fun insertGame(game: Game) =
        gamesRepository.insertWithoutPlayers(game)
    private suspend fun insertAllGames() =
        games.forEach {
            gamesRepository.insertWithoutPlayers(it)
        }
    @Test
    fun offlineGamesRepository_insertGame_insertsGameInDatabase() = runTest {
        insertGame(games[0])

        val allGames = gamesRepository.getAllGames().first()
        val expectedValue = games[0].clearPlayers()
        val actualValue = allGames[0]

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun offlineGamesRepository_insertGameWithoutIdSet_returnsCorrectGameId() = runTest {
        val gameId = insertGame(games[0].copy(id = 0))
        val expectedValue = 1
        val actualValue = gameId
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun offlineGamesRepository_insertGameWithIdSet_returnsCorrectGameId() = runTest {
        val testGame = games[0].copy(id = 167)
        val gameId = insertGame(testGame)
        val expectedValue = testGame.id
        val actualValue = gameId
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun offlineGamesRepository_getAllGamesStream_returnsAllGamesInDb() = runTest {
        insertAllGames()
        val expectedValue = games.map { it.clearPlayers() }
        val actualValue = gamesRepository.getAllGames().first()

        assertEquals(expectedValue, actualValue)
    }


    @Test
    fun offlineGamesRepository_deleteGame_deletesGame() = runTest {
        insertAllGames()
        gamesRepository.delete(games[0])

        val expectedValue = listOf(games[1].clearPlayers())
        val actualValues = gamesRepository.getAllGames().first()

        assertEquals(expectedValue, actualValues)
    }

    @Test
    fun offlineGamesRepository_getMaxGameId_returnsMaxGameID() = runTest {
        insertAllGames()

        val expectedValue = com.example.czechfoolapp.core.data.fake.FakeDataSource.maxGameID
        val actualValue = gamesRepository.getMaxGameID()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun offlineGamesRepository_doesGameExistByID_returnsTrue() = runTest {
        val testGame = games[0].clearPlayers()
        insertGame(testGame)
        assertTrue(gamesRepository.doesGameExistByID(testGame.id))
    }

    @Test
    fun offlineGamesRepository_getGame_returnsCorrectGame() = runTest {
        val testGame = games[0]
        insertGame(testGame)
        val expectedValue = testGame.clearPlayers()
        val actualValue = gamesRepository.getGame(testGame.id).first()
        assertEquals(expectedValue, actualValue)
    }
}



