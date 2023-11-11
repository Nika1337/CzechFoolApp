package com.example.czechfoolapp

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.repository.CurrentGameRepository
import com.example.czechfoolapp.data.repository.DefaultCurrentGameRepository
import com.example.czechfoolapp.data.repository.GamesRepository
import com.example.czechfoolapp.fake.FakeDataSource
import com.example.czechfoolapp.fake.FakeGamesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class DefaultCurrentGameRepositoryTest {
    private lateinit var currentGameRepository: CurrentGameRepository
    private lateinit var gamesRepository: GamesRepository

    @Before
    fun createCurrentGameRepository() {
        gamesRepository = FakeGamesRepository()
        currentGameRepository = DefaultCurrentGameRepository(
            gamesRepository = gamesRepository
        )
    }

    @Test
    fun defaultCurrentGameRepository_setGame_storesGivenGame() {
        val testGame = FakeDataSource.games[0]
        currentGameRepository.setGame(testGame)

        val expectedValue = testGame
        val actualValue = currentGameRepository.getCurrentGame()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentGameRepository_setGameWhenGameIsAlreadySet_throwsException() {
        val testGame1 = FakeDataSource.games[0]
        val testGame2 = FakeDataSource.games[1]

        currentGameRepository.setGame(testGame1)

        assertThrows(IllegalStateException::class.java) {
            currentGameRepository.setGame(testGame2)
        }
    }

    @Test
    fun defaultCurrentGameRepository_startGame_UpdatesCurrentGame() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0, isStarted = false)
        currentGameRepository.setGame(testGame)
        currentGameRepository.startGame()

        val expectedValue = testGame.copy(id = 1, isStarted = true)
        val actualValue = currentGameRepository.getCurrentGame()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentGameRepository_startGame_storesGame() = runTest {
        val testGame = FakeDataSource.games[1].copy(id = 0, isStarted = false)
        currentGameRepository.setGame(testGame)
        currentGameRepository.startGame()

        val expectedValue = testGame.copy(id = 1, isStarted = true)
        val actualValue = gamesRepository.getAllGames().first()[0]

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentGameRepository_startGameWhenGameIsAlreadyStarted_throwsException() {
        val testGame = FakeDataSource.games[0]
        currentGameRepository.setGame(testGame)

        assertThrows(IllegalStateException::class.java) {
            runTest {
                currentGameRepository.startGame()
            }
        }
    }

    @Test
    fun defaultCurrentGameRepository_startGameWhenCurrentGameNotSet_throwsException() {
        assertThrows(IllegalStateException::class.java) {
            runTest {
                currentGameRepository.startGame()
            }
        }
    }

    @Test
    fun defaultCurrentGameRepository_updateGame_updatesCurrentGame() {
        val testGame = FakeDataSource.games[0].copy(isStarted = false)
        currentGameRepository.setGame(testGame)
        currentGameRepository.updateGame(testGame.copy(losingScore = testGame.losingScore + 100))

        val expectedValue = testGame.copy(losingScore = testGame.losingScore + 100)
        val actualValue = currentGameRepository.getCurrentGame()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentGameRepository_updateGameWhenCurrentGameNotSet_throwsException() {
        val testGame = FakeDataSource.games[0]

        assertThrows(IllegalStateException::class.java) {
            currentGameRepository.updateGame(testGame)
        }
    }

    @Test
    fun defaultCurrentGameRepository_updateGameWhenCurrentGameIsStarted_throwsException() {
        val testGame = FakeDataSource.games[0]
        currentGameRepository.setGame(testGame)

        assertThrows(IllegalStateException::class.java) {
            currentGameRepository.updateGame(testGame.copy(losingScore = 120))
        }
    }

    @Test
    fun defaultCurrentGameRepository_cancelGame_cancelsGame() {
        val testGame = FakeDataSource.games[1]

        currentGameRepository.setGame(testGame)
        currentGameRepository.cancelGame()

        val expectedValue: Game? = null
        val actualValue = currentGameRepository.getCurrentGame()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentGameRepository_cancelGameWhenGameNotSet_throwsException() {
        assertThrows(IllegalStateException::class.java) {
            currentGameRepository.cancelGame()
        }
    }

    @Test
    fun defaultCurrentGameRepository_endGame_removesCurrentGame() = runTest {
        val testGame = FakeDataSource.games[0]
        currentGameRepository.setGame(testGame)
        currentGameRepository.endGame()

        val expectedValue: Game? = null
        val actualValue = currentGameRepository.getCurrentGame()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentGameRepository_endGame_updatesGameInDatabaseToFinished() = runTest {
        val testGame = FakeDataSource.games[0].copy(isStarted = false)
        currentGameRepository.setGame(testGame)
        currentGameRepository.startGame()
        currentGameRepository.endGame()

        val expectedValue: Game = testGame.copy(isStarted = true, isFinished = true)
        val actualValue = gamesRepository.getAllGames().first()[0]

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentGameRepository_endGameWhenGameIsNotSet_throwsException() {
        assertThrows(IllegalStateException::class.java) {
            runTest {
                currentGameRepository.endGame()
            }
        }
    }

    @Test
    fun defaultCurrentGameRepository_endGameWhenGameIsNotStarted_throwsException() {
        val testGame = FakeDataSource.games[0].copy(isStarted = false)
        currentGameRepository.setGame(testGame)
        assertThrows(IllegalStateException::class.java) {
            runTest {
                currentGameRepository.endGame()
            }
        }
    }
}








