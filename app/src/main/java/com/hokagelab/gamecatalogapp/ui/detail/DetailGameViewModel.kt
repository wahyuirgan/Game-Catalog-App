package com.hokagelab.gamecatalogapp.ui.detail

import com.hokagelab.gamecatalogapp.core.data.Resource
import com.hokagelab.gamecatalogapp.core.domain.model.Game
import com.hokagelab.gamecatalogapp.core.domain.usecase.GameUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow

class DetailGameViewModel(private val gameUseCase: GameUseCase) : ViewModel() {

    private var gameDetail: Flow<Resource<Game>>? = null

    fun setBookmarkGame(game: Game, newStatus: Boolean) = gameUseCase.setBookmarkGames(game, newStatus)

    fun setGameDetail(id: String) {
        gameDetail = gameUseCase.getGameDetail(id.toInt())
    }

    fun getGameDetail() = gameDetail?.asLiveData()
}