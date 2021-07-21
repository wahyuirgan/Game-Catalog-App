package com.hokagelab.gamecatalogapp.bookmark.di

import com.hokagelab.gamecatalogapp.bookmark.ui.bookmark.BookmarkViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object BookmarkModule {

    val bookmarkModule = module {
        viewModel { BookmarkViewModel(get()) }
    }
}