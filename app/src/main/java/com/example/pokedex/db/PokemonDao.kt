package com.example.pokedex.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex.model.Pokemon

@Dao
interface PokemonDao {
    // obtiene todos los Pokémon de la base de datos
    @Query("SELECT * FROM pokemon ORDER BY number ASC")
    suspend fun getAllPokemon(): List<Pokemon>
    // obtiene un Pokémon por su nombre
    @Query("SELECT * FROM pokemon WHERE name LIKE :query ORDER BY number ASC")
    suspend fun searchPokemonByName(query: String): List<Pokemon>
    // inserta una lista de Pokémon en la base de datos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<Pokemon>)
    // elimina todos los Pokémon de la base de datos
    @Query("DELETE FROM pokemon")
    suspend fun deleteAll()
}
