package apc.appcradle.pokemonstest.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonDAO(
    @PrimaryKey @ColumnInfo val name: String,
    @ColumnInfo val image: String,
    @ColumnInfo val personalUrl: String
)