package com.example.czechfoolapp

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.repository.GamesRepository
import com.example.czechfoolapp.data.repository.OfflineGamesRepository
import com.example.czechfoolapp.fake.FakeDataSource
import com.example.czechfoolapp.fake.FakeGameDao
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals


class OfflineGamesRepositoryTest {

    private lateinit var gamesRepository: GamesRepository
    @Before
    fun createRepository() {
        gamesRepository = OfflineGamesRepository(
            gameDao = FakeGameDao()
        )
    }

    private suspend fun insertGame(game: Game) {
        gamesRepository.insert(game)
    }
    private suspend fun insertAllGames() =
        FakeDataSource.games.forEach {
            gamesRepository.insert(it)
        }
    @Test
    fun offlineGamesRepository_insertGame_insertsGameInDatabase() = runTest {
        insertGame(FakeDataSource.games[0])

        val allGames = gamesRepository.getAllGames().first()
        val expectedValue = FakeDataSource.games[0]
        val actualValue = allGames[0]

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun offlineGamesRepository_getAllGamesStream_returnsAllGamesInDb() = runTest {
        insertAllGames()
        val expectedValue = FakeDataSource.games
        val actualValue = gamesRepository.getAllGames().first()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun offlineGamesRepository_updateGame_updatesGame() = runTest {
        insertAllGames()

        val expectedValue = FakeDataSource.games[1].copy(numberOfPlayers = 5)
        gamesRepository.update(expectedValue)
        val actualValue = gamesRepository.getAllGames().first().find {
            it.id == expectedValue.id
        }

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun offlineGamesRepository_deleteGame_deletesGame() = runTest {
        insertAllGames()
        gamesRepository.delete(FakeDataSource.games[0])

        val expectedValue = listOf(FakeDataSource.games[1])
        val actualValues = gamesRepository.getAllGames().first()

        assertEquals(expectedValue, actualValues)
    }

    @Test
    fun offlineGamesRepository_getMaxGameId_returnsMaxGameID() = runTest {
        insertAllGames()

        val expectedValue = FakeDataSource.maxGameId
        val actualValue = gamesRepository.getMaxGameId()

        assertEquals(expectedValue, actualValue)
    }


}



