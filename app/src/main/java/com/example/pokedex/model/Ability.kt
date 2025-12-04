package com.example.pokedex.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ability(val name: String, val isHidden: Boolean) : Parcelable
