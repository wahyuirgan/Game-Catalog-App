package com.hokagelab.gamecatalogapp.di

import com.hokagelab.gamecatalogapp.core.domain.usecase.GameInteractor
import com.hokagelab.gamecatalogapp.core.domain.usecase.GameUseCase
import com.hokagelab.gamecatalogapp.ui.detail.DetailGameViewModel
import com.hokagelab.gamecatalogapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val useCaseModule = module {
        factory<GameUseCase> { GameInteractor(get()) }
    }

    val viewModelModule = module {
        viewModel { HomeViewModel(get()) }
        viewModel { DetailGameViewModel(get()) }
    }
}