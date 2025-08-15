package apc.appcradle.pokemonstest.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import apc.appcradle.pokemonstest.ViewModel
import apc.appcradle.pokemonstest.data.LocalDatabase
import apc.appcradle.pokemonstest.data.LocalDatabaseConverter
import apc.appcradle.pokemonstest.data.LocalRepositoryImpl
import apc.appcradle.pokemonstest.data.NetworkClientImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModules = module {
    singleOf(::LocalRepositoryImpl)
    singleOf(::NetworkClientImpl)
    singleOf(::LocalDatabaseConverter)

    viewModelOf(::ViewModel)

    single<SharedPreferences> {
        androidContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            LocalDatabase::class.java, "database.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
}