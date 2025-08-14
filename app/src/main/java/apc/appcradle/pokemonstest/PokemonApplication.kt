package apc.appcradle.pokemonstest

import android.app.Application
import apc.appcradle.pokemonstest.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class PokemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PokemonApplication)
            modules(appModules)
        }
    }
}