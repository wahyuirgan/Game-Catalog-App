package com.hokagelab.gamecatalogapp

import com.hokagelab.gamecatalogapp.core.di.CoreModule.databaseModule
import com.hokagelab.gamecatalogapp.core.di.CoreModule.networkModule
import com.hokagelab.gamecatalogapp.core.di.CoreModule.repositoryModule
import android.app.Application
import com.hokagelab.gamecatalogapp.di.AppModule.useCaseModule
import com.hokagelab.gamecatalogapp.di.AppModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}