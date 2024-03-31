package com.example.czechfoolapp.core.domain

import com.example.czechfoolapp.core.data.repository.fake.FakeGamesRepository
import com.example.czechfoolapp.core.data.repository.fake.FakePlayersRepository
import com.example.czechfoolapp.core.domain.fake.FakeDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertThrows
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals


class StartNewGameUseCaseTest {
    private lateinit var subject: StartNewGameUseCase
    private lateinit var gamesRepository: FakeGamesRepository
    private lateinit var playersRepository: FakePlayersRepository

    @Before
    fun setup() {
        gamesRepository = FakeGamesRepository()
        playersRepository = FakePlayersRepository()
        subject = StartNewGameUseCase(
            gamesRepository = gamesRepository,
            playersRepository = playersRepository
        )
    }

    @Test
    fun startNewGameUseCase_startNewGameWithGameIdNonZero_throwsException() = runTest {
        val testGame = FakeDataSource.games[0]
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking {
                subject(testGame)
            }
        }
    }

    @Test
    fun startNewGameUseCase_startNewGame_insertsGame() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        subject(testGame)
        val expected = testGame.copy(id = 1)
        val actual = gamesRepository.getAllGames().first()[0]
        assertEquals(expected, actual)
    }

    @Test
    fun startNewGameUseCase_startNewGame_returnsCorrectGameId() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        val gameId = subject(testGame)
        val expected = 1
        val actual = gameId
        assertEquals(expected, actual)
    }
    @Test
    fun startNewGameUseCase_startNewGameWithGameAlreadyPutInGamesRepository_returnsCorrectGameId() = runTest {
        val games = FakeDataSource.games.map { it.copy(id = 0) }
        games.forEach {
            gamesRepository.insertWithoutPlayers(it)
        }
        val testGame = FakeDataSource.games[1].copy(id = 0)
        val gameId = subject(testGame)
        val expected = games.size + 1
        val actual = gameId

        assertEquals(expected, actual)
    }

    @Test
    fun startNewGameUseCase_startNewGame_insertsPlayers() = runTest {
        val testGame = FakeDataSource.games[0].copy(id = 0)
        subject(testGame)
        val expected = testGame.players
        val actual = playersRepository.getAllPlayersInGameSpecified(1).first()
        assertEquals(expected, actual)
    }

}
