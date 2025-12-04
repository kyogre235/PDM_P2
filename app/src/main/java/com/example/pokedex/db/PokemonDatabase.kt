package com.example.pokedex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.Item

@Database(entities = [Pokemon::class, Item::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: PokemonDatabase? = null
        // migracion de la base de datos de version 1 a 2 (no queria perder los datos, XD)
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `spriteUrl` TEXT NOT NULL, `cost` INTEGER NOT NULL, `effect` TEXT NOT NULL)")
            }
        }
        // obtiene la instancia de la base de datos
        fun getDatabase(context: Context): PokemonDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "pokemon_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
