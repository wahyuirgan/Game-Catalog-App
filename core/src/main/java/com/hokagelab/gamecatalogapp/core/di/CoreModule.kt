package com.hokagelab.gamecatalogapp.core.di

import com.hokagelab.gamecatalogapp.core.data.GameRepository
import com.hokagelab.gamecatalogapp.core.data.source.local.LocalDataSource
import com.hokagelab.gamecatalogapp.core.data.source.local.room.GameDatabase
import com.hokagelab.gamecatalogapp.core.data.source.remote.RemoteDataSource
import com.hokagelab.gamecatalogapp.core.data.source.remote.network.ApiInfo.BASE_URL
import com.hokagelab.gamecatalogapp.core.data.source.remote.network.ApiService
import com.hokagelab.gamecatalogapp.core.domain.repository.IGameRepository
import androidx.room.Room
import com.hokagelab.gamecatalogapp.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CoreModule {
    val databaseModule = module {
        factory { get<GameDatabase>().gameDao() }
        single {
            Room.databaseBuilder(
                androidContext(),
                GameDatabase::class.java, "dbgame.db"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }

    val networkModule = module {
        single {
            OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)
                .build()
        }
        single {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
            retrofit.create(ApiService::class.java)
        }
    }

    val repositoryModule = module {
        single { LocalDataSource(get()) }
        single { RemoteDataSource(get()) }
        factory { AppExecutors() }
        single<IGameRepository> {
            GameRepository(
                get(),
                get(),
                get()
            )
        }
    }
}