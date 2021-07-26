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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CoreModule {
    val databaseModule = module {
        factory { get<GameDatabase>().gameDao() }
        single {
            val passphrase: ByteArray = SQLiteDatabase.getBytes("cataloggame".toCharArray())
            val factory = SupportFactory(passphrase)
            Room.databaseBuilder(
                androidContext(),
                GameDatabase::class.java, "dbgame.db"
            ).fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
        }
    }

    val networkModule = module {
        single {
            val hostname = "api.rawg.io"
            val certificatePinner = CertificatePinner.Builder()
                .add(hostname, "sha256/UGwY2lttaRoHnGd1gpeydmov1LzioQpzYTywtNSJkAU=")
                .add(hostname, "sha256/hS5jJ4P+iQUErBkvoWBQOd1T7VOAYlOVegvv1iMzpxA=")
                .add(hostname, "sha256/R+V29DqDnO269dFhAAB5jMlZHepWpDGuoejXJjprh7A=")
                .add(hostname, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
                .build()
            OkHttpClient.Builder()
                .connectTimeout(150, TimeUnit.SECONDS)
                .readTimeout(150, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
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