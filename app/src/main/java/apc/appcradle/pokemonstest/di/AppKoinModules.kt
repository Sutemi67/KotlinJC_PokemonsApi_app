package apc.appcradle.pokemonstest.di

import android.content.Context
import android.content.SharedPreferences
import apc.appcradle.pokemonstest.ViewModel
import apc.appcradle.pokemonstest.data.LocalRepositoryImpl
import apc.appcradle.pokemonstest.data.NetworkClientImpl
import apc.appcradle.pokemonstest.domain.LocalRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModules = module {
    singleOf(::LocalRepositoryImpl)
    singleOf(::NetworkClientImpl)

    viewModelOf(::ViewModel)

    single<SharedPreferences> {
        androidContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
    }
}