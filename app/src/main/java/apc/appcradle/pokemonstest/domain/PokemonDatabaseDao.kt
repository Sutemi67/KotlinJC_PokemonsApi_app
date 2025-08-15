package apc.appcradle.pokemonstest.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import apc.appcradle.pokemonstest.domain.models.PokemonDAO

@Dao
interface PokemonDatabaseDao {
    @Query("SELECT * FROM pokemons")
    fun getAll(): List<PokemonDAO>

    @Query("SELECT * FROM pokemons WHERE name LIKE :name")
    fun findByName(name: String): PokemonDAO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PokemonDAO>)

    @Delete
    fun delete(user: PokemonDAO)

    @Query("delete from pokemons")
    fun clearDb()
}