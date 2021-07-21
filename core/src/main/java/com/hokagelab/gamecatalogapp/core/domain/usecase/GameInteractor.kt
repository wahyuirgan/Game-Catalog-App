package com.hokagelab.gamecatalogapp.core.domain.usecase

import com.hokagelab.gamecatalogapp.core.data.Resource
import com.hokagelab.gamecatalogapp.core.domain.model.Game
import com.hokagelab.gamecatalogapp.core.domain.repository.IGameRepository
import kotlinx.coroutines.flow.Flow

class GameInteractor(private val gameRepository: IGameRepository): GameUseCase {
    override fun getGames(): Flow<Resource<List<Game>>> = gameRepository.getGames()
    override fun getGameDetail(id: Int): Flow<Resource<Game>> = gameRepository.getGameDetail(id)
    override fun getBookmarkGames(): Flow<List<Game>> = gameRepository.getBookmarkGames()
    override fun setBookmarkGames(game: Game, state: Boolean) = gameRepository.setBookmarkGames(game, state)
}