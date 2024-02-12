package com.example.czechfoolapp

import androidx.datastore.core.DataStoreFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.czechfoolapp.datastore.CurrentGameDataSerializer
import com.example.czechfoolapp.datastore.CurrentGameDataSource
import com.example.czechfoolapp.datastore.DefaultCurrentGameDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith

private const val TEST_DATASTORE_NAME = "current_game_data_test.pb"
@RunWith(AndroidJUnit4::class)
class DefaultCurrentGameDataSourceTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private lateinit var defaultCurrentGameDataSource: CurrentGameDataSource

    @Before
    fun createDataStore() {
        val dataStore = DataStoreFactory.create(
            serializer = CurrentGameDataSerializer(),
            scope = testScope
        ) {
            tmpFolder.newFile(TEST_DATASTORE_NAME)
        }
        defaultCurrentGameDataSource = DefaultCurrentGameDataSource(dataStore)
    }

    @Test
    fun defaultCurrentGameDataSource_idZeroByDefault() = testScope.runTest {
        val expectedResult = 0
        val actualResult = defaultCurrentGameDataSource.getCurrentGameDataFlow().first().id
        assertEquals(expectedResult, actualResult)
    }
    @Test
    fun defaultCurrentGameDatasource_setCurrentGameID_updatesGameID() = testScope.runTest {
        val gameID = 1
        defaultCurrentGameDataSource.setCurrentGameID(gameID)
        val expectedResult = gameID
        val actualResult = defaultCurrentGameDataSource.getCurrentGameDataFlow().first().id
        assertEquals(expectedResult, actualResult)
    }
}