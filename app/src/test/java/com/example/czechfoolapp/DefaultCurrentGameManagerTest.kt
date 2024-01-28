package com.example.czechfoolapp

import com.example.czechfoolapp.data.repository.CurrentGameManager
import com.example.czechfoolapp.data.repository.DefaultCurrentGameManager
import com.example.czechfoolapp.data.repository.GamesRepository
import com.example.czechfoolapp.data.repository.PlayersRepository
import com.example.czechfoolapp.fake.FakeDataSource
import com.example.czechfoolapp.fake.FakeGamesRepository
import com.example.czechfoolapp.fake.FakePlayersRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DefaultCurrentGameManagerTest {
    private lateinit var currentGameManager: CurrentGameManager
    private lateinit var gamesRepository: GamesRepository
    private lateinit var playersRepository: PlayersRepository

    @Before
    fun createCurrentGameRepository() {
        playersRepository = FakePlayersRepository()
        gamesRepository = FakeGamesRepository()
        currentGameManager = DefaultCurrentGameManager(
            gamesRepository = gamesRepository,
            playersRepository = playersRepository
        )
    }

    @Test
    fun defaultCurrentGameManager_startNewGame_setsGameWithCorrectID() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        currentGameManager.startNewGame(testGame)

        val expectedValue = testGame.copy(id = 1)
        val actualValue = currentGameManager.getCurrentGame().first()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentGameManager_startNewGameWithNonZeroID_throwsException() = runTest {
        val testGame = FakeDataSource.games[0]

        assertThrows(IllegalArgumentException::class.java) {
             runBlocking {
                currentGameManager.startNewGame(testGame)
            }
        }
    }

    @Test
    fun defaultCurrentGameManager_startNewGameWhenGameIsAlreadyInProgress_throwsException() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        currentGameManager.startNewGame(testGame)
        val testGame2 = FakeDataSource.games[1].copy(id = 0)

        assertThrows(IllegalStateException::class.java) {
            runBlocking {
                currentGameManager.startNewGame(testGame2)
            }
        }
    }

    @Test
    fun defaultCurrentGameManager_continueGame_setsGameWithCorrectID() = runTest {
        val oldGame = FakeDataSource.games[0].copy(id = 0)
        currentGameManager.startNewGame(oldGame)
        currentGameManager.stopGame()

        currentGameManager.continueGame(1)
        val expectedValue = FakeDataSource.games[0].copy(id = 1)
        val actualValue = currentGameManager.getCurrentGame().first()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentGameManager_continueGameWhenGameAlreadyInProgress_throwsException() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        currentGameManager.startNewGame(testGame)

        assertThrows(IllegalStateException::class.java) {
            runBlocking {
                currentGameManager.continueGame(1)
            }
        }
    }

    @Test
    fun defaultCurrentGameManager_continueGameWithInvalidID_throwsException() = runTest {
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking {
                currentGameManager.continueGame(1)
            }
        }
    }

    @Test
    fun defaultCurrentGameManager_stopGame_getGameThrowsException() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        currentGameManager.startNewGame(testGame)
        currentGameManager.stopGame()

        assertThrows(IllegalStateException::class.java) {
            runBlocking {
                currentGameManager.getCurrentGame()
            }
        }
    }

    @Test
    fun defaultCurrentGameManager_stopGameWhenNoGameInProgress_throwsException() = runTest {
        assertThrows(IllegalStateException::class.java) {
            runBlocking {
                currentGameManager.stopGame()
            }
        }
    }

    @Test
    fun defaultCurrentGameManager_getCurrentGame_returnsCurrentGame() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        currentGameManager.startNewGame(testGame)
        val expectedValue = FakeDataSource.games[0].copy(id = 1)
        val actualValue = currentGameManager.getCurrentGame().first()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentGameManager_getCurrentGameWhenNoGameInProgress_throwsException() = runTest {
        assertThrows(IllegalStateException::class.java) {
            runBlocking {
                currentGameManager.getCurrentGame()
            }
        }
    }

    @Test
    fun defaultCurrentGameManager_updatePlayer_updatesPlayerInDatabase() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        currentGameManager.startNewGame(testGame)

        val testPlayer = FakeDataSource.games[0].players[0].copy(score = 100)
        currentGameManager.updatePlayer(testPlayer)

        val expectedValue = testPlayer
        val actualValue = playersRepository.getAllPlayersInGameSpecified(1).first()[0]

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentGameManager_updatePlayerWhenNoGameSet_throwsException() = runTest {
        assertThrows(IllegalStateException::class.java) {
            runBlocking {
                currentGameManager.updatePlayer(FakeDataSource.games[0].players[0])
            }
        }
    }

    @Test
    fun defaultCurrentGameManager_updatePlayerWithWrongID_doesNothing() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        currentGameManager.startNewGame(testGame)
        val testPlayer = testGame.players[0].copy(id = 5)
        currentGameManager.updatePlayer(testPlayer)

        val expectedPlayers = testGame.players
        val actualPlayers = playersRepository.getAllPlayersInGameSpecified(1).first()

        assertEquals(expectedPlayers, actualPlayers)
    }

    @Test
    fun defaultCurrentGameManager_isGameInProgressWhenGameNotSet_returnsFalse() = runTest {
        assertFalse(currentGameManager.isGameInProgress())
    }

    @Test
    fun defaultCurrentGameManager_isGameInProgressAfterGameStarted_returnsTrue() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        currentGameManager.startNewGame(testGame)
        assertTrue(currentGameManager.isGameInProgress())
    }

    @Test
    fun defaultCurrentGameManager_isGameInProgressAfterGameStopped_returnsFalse() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        currentGameManager.startNewGame(testGame)
        currentGameManager.stopGame()
        assertFalse(currentGameManager.isGameInProgress())
    }

    @Test
    fun defaultCurrentGameManager_isGameInProgressAfterGameContinued_returnsTrue() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        currentGameManager.startNewGame(testGame)
        currentGameManager.stopGame()
        currentGameManager.continueGame(1)
        assertTrue(currentGameManager.isGameInProgress())
    }
}