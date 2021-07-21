package com.hokagelab.gamecatalogapp.bookmark.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hokagelab.gamecatalogapp.core.domain.usecase.GameUseCase

class BookmarkViewModel(gameUseCase: GameUseCase): ViewModel() {
    val bookmarkGames = gameUseCase.getBookmarkGames().asLiveData()
}