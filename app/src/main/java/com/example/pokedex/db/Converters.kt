package com.example.pokedex.db

import androidx.room.TypeConverter
import com.example.pokedex.model.Ability
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromAbilityList(abilities: List<Ability>?): String? {
        return gson.toJson(abilities)
    }

    @TypeConverter
    fun toAbilityList(abilitiesString: String?): List<Ability>? {
        if (abilitiesString == null) {
            return null
        }
        val listType = object : TypeToken<List<Ability>>() {}.type
        return gson.fromJson(abilitiesString, listType)
    }

    @TypeConverter
    fun fromStatsMap(stats: Map<String, Int>?): String? {
        return gson.toJson(stats)
    }

    @TypeConverter
    fun toStatsMap(statsString: String?): Map<String, Int>? {
        if (statsString == null) {
            return null
        }
        val mapType = object : TypeToken<Map<String, Int>>() {}.type
        return gson.fromJson(statsString, mapType)
    }
}
