package com.example.czechfoolapp

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.repository.GamesRepository
import com.example.czechfoolapp.data.repository.OfflineGamesRepository
import com.example.czechfoolapp.fake.FakeDataSource
import com.example.czechfoolapp.fake.FakeGameDao
import com.example.czechfoolapp.util.clearPlayers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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

    private suspend fun insertGame(game: Game) {
        gamesRepository.insertWithoutPlayers(game)
    }
    private suspend fun insertAllGames() =
        FakeDataSource.games.forEach {
            gamesRepository.insertWithoutPlayers(it)
        }
    @Test
    fun offlineGamesRepository_insertGame_insertsGameInDatabase() = runTest {
        insertGame(FakeDataSource.games[0])

        val allGames = gamesRepository.getAllGames().first()
        val expectedValue = FakeDataSource.games[0].clearPlayers()
        val actualValue = allGames[0]

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun offlineGamesRepository_getAllGamesStream_returnsAllGamesInDb() = runTest {
        insertAllGames()
        val expectedValue = FakeDataSource.games.map { it.clearPlayers() }
        val actualValue = gamesRepository.getAllGames().first()

        assertEquals(expectedValue, actualValue)
    }


    @Test
    fun offlineGamesRepository_deleteGame_deletesGame() = runTest {
        insertAllGames()
        gamesRepository.delete(FakeDataSource.games[0])

        val expectedValue = listOf(FakeDataSource.games[1].clearPlayers())
        val actualValues = gamesRepository.getAllGames().first()

        assertEquals(expectedValue, actualValues)
    }

    @Test
    fun offlineGamesRepository_getMaxGameId_returnsMaxGameID() = runTest {
        insertAllGames()

        val expectedValue = FakeDataSource.maxGameID
        val actualValue = gamesRepository.getMaxGameID()

        assertEquals(expectedValue, actualValue)
    }

}



