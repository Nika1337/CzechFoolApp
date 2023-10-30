import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.czechfoolapp.database.CzechFoolGameDatabase
import com.example.czechfoolapp.database.dao.GameDao
import com.example.czechfoolapp.database.model.GameEntity
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
class GameDaoTest {
    private lateinit var gameDao: GameDao
    private lateinit var czechFoolGameDatabase: CzechFoolGameDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        czechFoolGameDatabase = Room.inMemoryDatabaseBuilder(context, CzechFoolGameDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        gameDao = czechFoolGameDatabase.gameDao()
    }

    @After
    fun closeDb() {
        czechFoolGameDatabase.close()
    }

    private val gameEntity1 =
        GameEntity(
            id = 1,
            losingScore = 200,
            numberOfPlayers = 4,
            date = LocalDateTime.now()
        )

    private val gameEntity2 =
        GameEntity(
            id = 2,
            losingScore = 300,
            numberOfPlayers = 3,
            date = LocalDateTime.now()
        )

    private suspend fun addOneGameToDb() {
        gameDao.insert(gameEntity1)
    }

    private suspend fun addTwoGamesToDb() {
        gameDao.insert(gameEntity1)
        gameDao.insert(gameEntity2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsGameIntoDb() = runBlocking {
        addOneGameToDb()
        val allGames = gameDao.getAllGames().first()
        assertEquals(allGames[0], gameEntity1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllGames_returnsAllGamesFromDb() = runBlocking {
        addTwoGamesToDb()
        val allItems = gameDao.getAllGames().first()
        assertEquals(allItems[0], gameEntity1)
        assertEquals(allItems[1], gameEntity2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateGames_updatesGamesInDb() = runBlocking {
        val gameEntity1Copy = gameEntity1.copy(numberOfPlayers = 123)
        val gameEntity2Copy = gameEntity2.copy(losingScore = 123)

        addTwoGamesToDb()
        gameDao.update(gameEntity1Copy)
        gameDao.update(gameEntity2Copy)

        val allGames = gameDao.getAllGames().first()
        assertEquals(gameEntity1Copy, allGames[0])
        assertEquals(gameEntity2Copy, allGames[1])
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteGames_deletesAllGamesFromDb() = runBlocking {
        addTwoGamesToDb()
        gameDao.delete(gameEntity1)
        gameDao.delete(gameEntity2)

        val allGames = gameDao.getAllGames().first()
        assertTrue(allGames.isEmpty())
    }
}












