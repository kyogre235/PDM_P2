package com.example.pokedex.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey
    val number: Int,
    val name: String,
    val spriteUrl: String,
    val type1: String,
    val type2: String? = null,
    val pokedexDescription: String,
    val abilities: List<Ability>,
    val stats: Map<String, Int>
) : Parcelable
