package com.example.pokedex.lector

import com.example.pokedex.Ability
import com.example.pokedex.Pokemon
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// --- Data Class para trabajar con la PokeApi de forma comoda---

data class PokemonListResponse(@SerializedName("results") val results: List<PokemonListItem>)

data class PokemonListItem(val name: String, val url: String)

data class PokemonDetailsResponse(
    val id: Int,
    val name: String,
    val types: List<TypeResponse>,
    val abilities: List<AbilityResponse>,
    val stats: List<StatResponse>,
    val sprites: SpritesResponse
)

data class SpritesResponse(@SerializedName("front_default") val frontDefault: String)

data class TypeResponse(val type: TypeDetails)

data class TypeDetails(val name: String)

data class AbilityResponse(val ability: AbilityDetails, @SerializedName("is_hidden") val isHidden: Boolean)

data class AbilityDetails(val name: String)

data class StatResponse(val stat: StatDetails, @SerializedName("base_stat") val baseStat: Int)

data class StatDetails(val name: String)

data class PokemonSpeciesResponse(
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>
)

data class FlavorTextEntry(
    @SerializedName("flavor_text") val flavorText: String,
    val language: Language
)

data class Language(val name: String)

// ---Servicio Retrofit ---

/*
* Interfaz que define los endpoints de la API de Pokemon.
* */
interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int = 151): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetailsResponse

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpeciesResponse
}

// --- Clase Main API  ---

class PokeApi {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/") // PokeAPI URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: PokeApiService = retrofit.create(PokeApiService::class.java)

    suspend fun getPokemonList(): List<Pokemon> = withContext(Dispatchers.IO) {
        val pokemonListResponse = service.getPokemonList()

        // lo asemos asincrono para que la aplicacion no se quede trabada hasta que complete la peticion
        // igual nos permite poner el cargando... mas adelante en el viewmodel

        val deferredPokemonDetails = pokemonListResponse.results.map { pokemonListItem ->
            async {
                val details = service.getPokemonDetails(pokemonListItem.name)
                val species = service.getPokemonSpecies(details.id)
                mapToPokemon(details, species)
            }
        }

        deferredPokemonDetails.awaitAll().sortedBy { it.number } //como es asincrono, los tendremos que ordenar, lo hacemos usando el numero de pokedex
    }

    private fun mapToPokemon(details: PokemonDetailsResponse, species: PokemonSpeciesResponse): Pokemon {
        // le pasamos el idioma que que queremos en la descripcion, para que este en español
        val description = species.flavorTextEntries
            .firstOrNull { it.language.name == "es" }?.flavorText
            ?.replace("\n", " ") ?: "Descripción no disponible."

        val types = details.types.map { it.type.name.replaceFirstChar { char -> char.uppercase() } }
        val abilities = details.abilities.map { Ability(it.ability.name.replaceFirstChar { char -> char.uppercase() }, it.isHidden) }
        //casteamos los nombres de las stats a español
        val stats = details.stats.associate {
            val statName = when (it.stat.name) {
                "hp" -> "HP"
                "attack" -> "Ataque"
                "defense" -> "Defensa"
                "special-attack" -> "Sp. Atk"
                "special-defense" -> "Sp. Def"
                "speed" -> "Velocidad"
                else -> it.stat.name
            }
            statName to it.baseStat
        }

        return Pokemon(
            number = details.id,
            name = details.name.replaceFirstChar { it.uppercase() },
            spriteUrl = details.sprites.frontDefault,
            type1 = types.getOrNull(0) ?: "",
            type2 = types.getOrNull(1),
            pokedexDescription = description,
            abilities = abilities,
            stats = stats
        )
    }
}