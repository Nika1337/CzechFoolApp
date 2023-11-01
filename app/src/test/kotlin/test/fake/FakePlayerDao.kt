package test.fake

import com.example.czechfoolapp.database.dao.PlayerDao
import com.example.czechfoolapp.database.model.PlayerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakePlayerDao : PlayerDao {
    private val currentPlayers: MutableList<PlayerEntity> = mutableListOf()
    override suspend fun insertAll(vararg players: PlayerEntity) {
        currentPlayers.addAll(players)
    }

    override suspend fun delete(player: PlayerEntity) {
        currentPlayers.remove(player)
    }

    override suspend fun update(player: PlayerEntity) {
        currentPlayers.forEachIndexed() { index: Int, it: PlayerEntity ->
            if (it.gameId == player.gameId && it.name == player.name) {
                currentPlayers[index] = player
            }
        }
    }

    override fun getAllPlayersInGameSpecified(gameID: Int): Flow<List<PlayerEntity>> {
        val resultList: MutableList<PlayerEntity> = mutableListOf()
        currentPlayers.forEach {
            if (it.gameId == gameID) {
                resultList.add(it)
            }
        }
        return flowOf(
            resultList
        )
    }

    override fun getPlayer(gameID: Int, name: String): PlayerEntity {
        currentPlayers.forEach {
            if (it.gameId == gameID && it.name == name) {
                return it
            }
        }
        throw Exception("No such player")
    }
}