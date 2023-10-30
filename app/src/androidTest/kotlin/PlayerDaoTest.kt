import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.czechfoolapp.database.CzechFoolGameDatabase
import com.example.czechfoolapp.database.dao.PlayerDao
import com.example.czechfoolapp.database.model.GameEntity
import com.example.czechfoolapp.database.model.PlayerEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class PlayerDaoTest {
    private lateinit var playerDao: PlayerDao
    private lateinit var czechFoolGameDatabase: CzechFoolGameDatabase
    private val defaultGameId = 1



    private val playerEntity1 =
        PlayerEntity(
            gameId = defaultGameId,
            name = "Player1",
            score = 0
        )

    private val playerEntity2 =
        PlayerEntity(
            gameId = defaultGameId,
            name = "Player2",
            score = 0
        )
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        czechFoolGameDatabase = Room.inMemoryDatabaseBuilder(context, CzechFoolGameDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        playerDao = czechFoolGameDatabase.playerDao()
        populateWithGame()
    }

    private fun populateWithGame() = runBlocking {
        val gameDao = czechFoolGameDatabase.gameDao()
        val gameEntity1 =
            GameEntity(
                id = defaultGameId,
                losingScore = 200,
                numberOfPlayers = 4,
                date = LocalDateTime.now()
            )
        gameDao.insert(gameEntity1)
    }

    @After
    fun closeDb() {
        czechFoolGameDatabase.close()
    }

    private suspend fun addOnePlayerToDb() {
        playerDao.insertAll(playerEntity1)
    }

    private suspend fun addTwoPlayersToDb() {
        playerDao.insertAll(playerEntity1, playerEntity2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsPlayerIntoDb() = runBlocking {
        addOnePlayerToDb()
        val allPlayers = playerDao.getAllPlayersInGameSpecified(defaultGameId).first()
        assertEquals(playerEntity1, allPlayers[0])
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDb() = runBlocking {
        addTwoPlayersToDb()
        val allItems = playerDao.getAllPlayersInGameSpecified(defaultGameId).first()
        assertEquals(allItems[0], playerEntity1)
        assertEquals(allItems[1], playerEntity2)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetPlayer_returnsCorrectPlayerFromDb() = runBlocking {
        addTwoPlayersToDb()
        val expectedPlayerEntity1 = playerDao.getPlayer(defaultGameId, playerEntity1.name)
        val expectedPlayerEntity2 = playerDao.getPlayer(defaultGameId, playerEntity2.name)

        assertEquals(playerEntity1, expectedPlayerEntity1)
        assertEquals(playerEntity2, expectedPlayerEntity2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdatePlayer_updatesPlayerInDb() = runBlocking {
        val playerEntity1Copy = playerEntity1.copy(score = 67)

        addOnePlayerToDb()
        playerDao.update(playerEntity1Copy)

        val allPlayers = playerDao.getAllPlayersInGameSpecified(defaultGameId).first()
        assertEquals(playerEntity1Copy, allPlayers[0])
    }

    @Test
    @Throws(Exception::class)
    fun daoDeletePlayers_deletesAllPlayersFromDb() = runBlocking {
        addTwoPlayersToDb()
        playerDao.delete(playerEntity1)
        playerDao.delete(playerEntity2)

        val allPlayers = playerDao.getAllPlayersInGameSpecified(defaultGameId).first()
        assertTrue(allPlayers.isEmpty())
    }

}


