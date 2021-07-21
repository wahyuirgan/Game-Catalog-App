package com.hokagelab.gamecatalogapp.ui.home

import com.hokagelab.gamecatalogapp.core.domain.usecase.GameUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class HomeViewModel(gameUseCase: GameUseCase) : ViewModel() {
    val games = gameUseCase.getGames().asLiveData()
}