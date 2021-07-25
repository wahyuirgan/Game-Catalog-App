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
                .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
                .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
                .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
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