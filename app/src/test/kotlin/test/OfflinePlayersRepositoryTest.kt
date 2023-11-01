package test

import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.data.repository.OfflinePlayersRepository
import com.example.czechfoolapp.data.repository.PlayersRepository
import test.fake.FakeDataSource
import test.fake.FakePlayerDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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

    private suspend fun insertPlayer(player: Player) =
        playersRepository.insertAll(player)

    private suspend fun insertAllPlayers() =
        playersRepository.insertAll(*(FakeDataSource.players.toTypedArray()))

    @Test
    fun offlinePlayersRepository_insertPlayer_insertsPlayerInDatabase() = runTest {
        val testPlayer = FakeDataSource.players[0]
        insertPlayer(testPlayer)

        val allPlayers = playersRepository.getAllPlayersInGameSpecified(testPlayer.gameId).first()
        val expectedPlayer = testPlayer
        val actualPlayer = allPlayers[0]

        assertEquals(expectedPlayer, actualPlayer)
    }

    @Test
    fun offlinePlayersRepository_getAllPlayersInGameSpecified_returnsAllPlayersInAGivenGame() = runTest {
        insertAllPlayers()

        val expectedPlayersInFirstGame = FakeDataSource.players.filter {
            it.gameId == FakeDataSource.gameId1
        }
        val expectedPlayersInSecondGame = FakeDataSource.players.filter {
            it.gameId == FakeDataSource.gameId2
        }
        val actualPlayersInFirstGame =
            playersRepository
                .getAllPlayersInGameSpecified(FakeDataSource.gameId1)
                .first()

        val actualPlayersInSecondGame =
            playersRepository
                .getAllPlayersInGameSpecified(FakeDataSource.gameId2)
                .first()

        assertEquals(expectedPlayersInFirstGame, actualPlayersInFirstGame)
        assertEquals(expectedPlayersInSecondGame, actualPlayersInSecondGame)
    }

    @Test
    fun offlinePlayersRepository_getPlayer_returnsPlayer() = runTest {
        val testPlayer = FakeDataSource.players[5]
        insertPlayer(testPlayer)

        val expectedPlayer = testPlayer
        val actualPlayer = playersRepository.getPlayer(
            gameID = testPlayer.gameId,
            name = testPlayer.name
        )

        assertEquals(expectedPlayer, actualPlayer)
    }

    @Test
    fun offlinePlayersRepository_updatePlayer_updatesPlayer() = runTest {
        insertAllPlayers()
        val testPlayer = FakeDataSource.players[3].let {
            it.copy(score = it.score + 33)
        }
        playersRepository.update(testPlayer)

        val expectedPlayer = testPlayer
        val actualPlayer = playersRepository.getPlayer(
            gameID = testPlayer.gameId,
            name = testPlayer.name
        )
        assertEquals(expectedPlayer, actualPlayer)
    }
}





