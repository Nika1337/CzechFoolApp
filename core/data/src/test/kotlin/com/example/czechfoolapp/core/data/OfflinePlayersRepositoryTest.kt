package com.example.czechfoolapp.core.data

import com.example.czechfoolapp.core.data.fake.FakePlayerDao
import com.example.czechfoolapp.core.data.repository.OfflinePlayersRepository
import com.example.czechfoolapp.core.data.repository.PlayersRepository
import com.example.czechfoolapp.core.model.Player
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class OfflinePlayersRepositoryTest {
    private lateinit var playersRepository: PlayersRepository
    @Before
    fun createRepository() {
        playersRepository = OfflinePlayersRepository(
            playerDao = FakePlayerDao()
        )
    }

    private suspend fun insertPlayer(player: Player, gameID: Int) =
        playersRepository.insertAll(
            player,
            gameID = gameID
        )

    private suspend fun insertAllPlayers() = com.example.czechfoolapp.core.data.fake.FakeDataSource.players.forEach {
        playersRepository.insertAll(
            players = it.value.toTypedArray(),
            gameID = it.key
        )
    }
    @Test
    fun offlinePlayersRepository_insertPlayer_insertsPlayerInDatabase() = runTest {
        val gameID = com.example.czechfoolapp.core.data.fake.FakeDataSource.gameId1
        val testPlayer = com.example.czechfoolapp.core.data.fake.FakeDataSource.players[gameID]!![0]
        insertPlayer(testPlayer, gameID)

        val allPlayers = playersRepository.getAllPlayersInGameSpecified(gameID = gameID).first()
        val expectedPlayer = testPlayer
        val actualPlayer = allPlayers[0]

        assertEquals(expectedPlayer, actualPlayer)
    }

    @Test
    fun offlinePlayersRepository_getAllPlayersInGameSpecified_returnsAllPlayersInAGivenGame() = runTest {
        insertAllPlayers()
        val expectedPlayersInFirstGame = com.example.czechfoolapp.core.data.fake.FakeDataSource.players[com.example.czechfoolapp.core.data.fake.FakeDataSource.gameId1]!!
        val expectedPlayersInSecondGame = com.example.czechfoolapp.core.data.fake.FakeDataSource.players[com.example.czechfoolapp.core.data.fake.FakeDataSource.gameId2]!!

        val actualPlayersInFirstGame =
            playersRepository
                .getAllPlayersInGameSpecified(com.example.czechfoolapp.core.data.fake.FakeDataSource.gameId1)
                .first()

        val actualPlayersInSecondGame =
            playersRepository
                .getAllPlayersInGameSpecified(com.example.czechfoolapp.core.data.fake.FakeDataSource.gameId2)
                .first()

        assertEquals(expectedPlayersInFirstGame, actualPlayersInFirstGame)
        assertEquals(expectedPlayersInSecondGame, actualPlayersInSecondGame)
    }

    @Test
    fun offlinePlayersRepository_getPlayer_returnsPlayer() = runTest {
        val gameID = com.example.czechfoolapp.core.data.fake.FakeDataSource.gameId2
        val testPlayer = com.example.czechfoolapp.core.data.fake.FakeDataSource.players[gameID]!!.first()
        insertPlayer(testPlayer, gameID)

        val expectedPlayer = testPlayer
        val actualPlayer = playersRepository.getAllPlayersInGameSpecified(gameID).first()[0]

        assertEquals(expectedPlayer, actualPlayer)
    }

    @Test
    fun offlinePlayersRepository_updatePlayer_updatesPlayer() = runTest {
        val gameID = com.example.czechfoolapp.core.data.fake.FakeDataSource.gameId2
        val testPlayer = com.example.czechfoolapp.core.data.fake.FakeDataSource.players[gameID]!![0]
        insertPlayer(testPlayer, gameID)
        val updatedTestPlayer = testPlayer.let {
            it.copy(score = it.score + 33)
        }
        playersRepository.update(updatedTestPlayer, gameID)

        val expectedPlayer = updatedTestPlayer
        val actualPlayer = playersRepository.getAllPlayersInGameSpecified(gameID).first()[0]

        assertEquals(expectedPlayer, actualPlayer)
    }

    @Test
    fun offlinePlayersRepository_deletePlayer_deletesPlayer() = runTest {
        val gameID = com.example.czechfoolapp.core.data.fake.FakeDataSource.gameId1
        val testPlayer = com.example.czechfoolapp.core.data.fake.FakeDataSource.players[gameID]!![0]
        insertPlayer(testPlayer, gameID)

        playersRepository.delete(testPlayer, gameID)
        val allGames = playersRepository.getAllPlayersInGameSpecified(gameID).first()

        assertTrue(allGames.isEmpty())
    }
}





