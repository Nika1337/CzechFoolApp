package com.example.czechfoolapp

import com.example.czechfoolapp.data.repository.DefaultCurrentPlayerNamesRepository
import com.example.czechfoolapp.fake.FakeDataSource
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class DefaultCurrentPlayerNamesRepositoryTest {
    private lateinit var currentPlayerNamesRepository: DefaultCurrentPlayerNamesRepository

    @Before
    fun createCurrentPlayersRepository() {
        currentPlayerNamesRepository = DefaultCurrentPlayerNamesRepository()
    }
    @Test
    fun defaultCurrentPlayerNamesRepository_setPlayers_setsPlayers() {
        val testPlayerNames = FakeDataSource.players.map { it.name }
        currentPlayerNamesRepository.setPlayerNames(testPlayerNames)

        val expectedValue = testPlayerNames
        val actualValue = currentPlayerNamesRepository.getCurrentPlayerNames()

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun defaultCurrentPlayerNamesRepository_clearCurrentPlayerNames_clearsCurrentPlayerNames() {
        val testPlayerNames = FakeDataSource.players.map { it.name }
        currentPlayerNamesRepository.setPlayerNames(testPlayerNames)
        currentPlayerNamesRepository.clearCurrentPlayerNames()

        val expectedValue = listOf<String>()
        val actualValue = currentPlayerNamesRepository.getCurrentPlayerNames()

        assertEquals(expectedValue, actualValue)
    }


}