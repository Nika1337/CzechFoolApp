package com.example.czechfoolapp.data.repository

import android.util.Log
import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.datastore.CurrentGameDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

/**
 * Default value of CurrentGameData.id
 */
private const val NO_GAME_ID = 0
class DefaultCurrentGameManager(
    private val gamesRepository: GamesRepository,
    private val playersRepository: PlayersRepository,
    private val currentGameDataSource: CurrentGameDataSource
) : CurrentGameManager {
    private val lastSavedCurrentGameIDFlow = currentGameDataSource.getCurrentGameDataFlow().map { it.id }
    private var currentGameID = NO_GAME_ID
    override fun isGameInProgress() = currentGameID != NO_GAME_ID

    override suspend fun startNewGame(game: Game) {
        if (game.id != 0) {
            throw IllegalArgumentException("New Game ID should always be zero")
        }
        if (currentGameID != NO_GAME_ID) {
            throw IllegalStateException("Game already in progress")
        }
        gamesRepository.insertWithoutPlayers(game)
        val maxGameID = gamesRepository.getMaxGameID()
        currentGameDataSource.setCurrentGameID(maxGameID)
        currentGameID = maxGameID
        playersRepository.insertAll(
            players = game.players.toTypedArray(),
            gameID = currentGameID
        )
    }

    override suspend fun continueGame(gameID: Int) {
        if (currentGameID != NO_GAME_ID) {
            throw IllegalStateException("Game already in progress")
        }
        val doesGameExistByID = gamesRepository.doesGameExistByID(gameID)
        if (doesGameExistByID.not()) {
            throw IllegalArgumentException("No game with given id stored")
        }
        currentGameDataSource.setCurrentGameID(gameID)
        currentGameID = gameID
    }

    override suspend fun stopGame() {
        if (currentGameID == NO_GAME_ID) {
            throw IllegalStateException("No game in progress")
        }
        currentGameDataSource.setCurrentGameID(NO_GAME_ID)
        currentGameID = NO_GAME_ID
    }

    override fun getCurrentGameFlow(): Flow<Game> {
        if (currentGameID == NO_GAME_ID) {
            throw IllegalStateException("No game in progress")
        }
        return gamesRepository.getGame(currentGameID).map { game ->
            game ?: throw IllegalStateException("No game in database with given id")
        }
    }

    override suspend fun updatePlayer(player: Player) {
        if (currentGameID == NO_GAME_ID) {
            throw IllegalStateException("No game in progress")
        }
        playersRepository.update(
            player = player,
            gameID = currentGameID
        )
    }

    /**
     * Restores last saved game from persistent storage,
     * used for restoring after process death,
     * if the currentGameID is already set, does nothing
     */
    override suspend fun restoreLastSavedGame() {
        if (currentGameID != NO_GAME_ID) {
            return
        }
        currentGameID = lastSavedCurrentGameIDFlow.first()
    }

}