package test.fake

import com.example.czechfoolapp.database.dao.GameDao
import com.example.czechfoolapp.database.model.GameEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGameDao : GameDao {
    private var currentGames: MutableList<GameEntity> = mutableListOf()
    override suspend fun insert(game: GameEntity) {
        currentGames.add(game)
    }

    override suspend fun delete(game: GameEntity) {
        currentGames.remove(game)
    }

    override suspend fun update(game: GameEntity) {
        currentGames = currentGames.map {
            if (it.id == game.id) {
                println("Found $it $game")
                game
            } else {
                println("Not Found $it $game")
                it
            }
        }.toMutableList()
    }

    override fun getAllGames(): Flow<List<GameEntity>> {
        return flowOf(
            currentGames
        )
    }
}