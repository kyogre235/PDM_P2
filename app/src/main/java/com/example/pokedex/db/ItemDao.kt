package com.example.pokedex.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex.model.Item

@Dao
interface ItemDao {
    // obtiene todos los items de la base de datos
    @Query("SELECT * FROM items ORDER BY name ASC")
    suspend fun getAllItems(): List<Item>
    // obtiene un item por su nombre
    @Query("SELECT * FROM items WHERE name LIKE :query ORDER BY name ASC")
    suspend fun searchItemByName(query: String): List<Item>
    // inserta una lista de items en la base de datos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Item>)
    // elimina todos los items de la base de datos
    @Query("DELETE FROM items")
    suspend fun deleteAll()
}
