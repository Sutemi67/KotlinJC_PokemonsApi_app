package apc.appcradle.pokemonstest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import apc.appcradle.pokemonstest.domain.PokemonDatabaseDao
import apc.appcradle.pokemonstest.domain.models.PokemonDAO

@Database(entities = [PokemonDAO::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): PokemonDatabaseDao
}