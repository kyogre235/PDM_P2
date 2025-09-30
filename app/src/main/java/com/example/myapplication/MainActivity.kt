package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                    ) {
                    PokemonListScreen()
                    // PokemonDetailScreen()  // Para ver la segunda vista
                }
            }
        }
    }
}

// Data class para representar un Pokémon
data class Pokemon(
    val id: Int,
    val name: String,
    val type: String,
    val isCaptured: Boolean = false
)

// Barra superior
@Composable
fun CustomTopBar(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE53935))
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// Vista 1: Lista de Pokémon
@Composable
fun PokemonListScreen() {
    var pokemonList by remember { mutableStateOf(getSamplePokemon()) }

    Column(modifier = Modifier.fillMaxSize()) {
        CustomTopBar(title = "Pokédex")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(pokemonList) { pokemon ->
                PokemonListItem(
                    pokemon = pokemon,
                    onCaptureToggle = {
                        pokemonList = pokemonList.map {
                            if (it.id == pokemon.id) it.copy(isCaptured = !it.isCaptured)
                            else it
                        }
                    },
                    onEditClick = {
                        // Aquí irá la navegación después
                        println("Editando ${pokemon.name}")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun PokemonListItem(
    pokemon: Pokemon,
    onCaptureToggle: () -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "#${pokemon.id.toString().padStart(3, '0')} ${pokemon.name}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = pokemon.type,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }

            Row {
                // Botón de check (capturado)
                Button(
                    onClick = onCaptureToggle,
                    modifier = Modifier.size(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (pokemon.isCaptured) Color(0xFF4CAF50) else Color(0xFFE0E0E0)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Capturado",
                        tint = if (pokemon.isCaptured) Color.White else Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Botón de edición
                Button(
                    onClick = onEditClick,
                    modifier = Modifier.size(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

// Vista 2: Edición de Pokémon
@Composable
fun PokemonDetailScreen() {
    var pokemonName by remember { mutableStateOf("Pikachu") }
    var pokemonDescription by remember { mutableStateOf("Pokémon tipo eléctrico") }
    var isCaptured by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        CustomTopBar(title = "Detalles Pokémon")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Campo de texto para nombre
            Text(
                text = "Nombre:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = pokemonName,
                onValueChange = { pokemonName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            // Campo de texto para descripción
            Text(
                text = "Descripción:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = pokemonDescription,
                onValueChange = { pokemonDescription = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 16.dp),
                singleLine = false
            )

            // Fila para el checkbox de capturado
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Checkbox(
                    checked = isCaptured,
                    onCheckedChange = { isCaptured = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF4CAF50)
                    )
                )
                Text(
                    text = "Capturado",
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 16.sp
                )
            }

            // Botón de guardar
            Button(
                onClick = {
                    println("Guardado: $pokemonName")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935)
                )
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}

// Función para obtener datos de ejemplo
fun getSamplePokemon(): List<Pokemon> {
    return listOf(
        Pokemon(1, "Bulbasaur", "Planta/Veneno", true),
        Pokemon(4, "Charmander", "Fuego", false),
        Pokemon(7, "Squirtle", "Agua", true),
        Pokemon(25, "Pikachu", "Eléctrico", true),
        Pokemon(150, "Mewtwo", "Psíquico", false)
    )
}

@Preview(showBackground = true)
@Composable
fun PokemonListPreview() {
    MyApplicationTheme {
        PokemonListScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailPreview() {
    MyApplicationTheme {
        PokemonDetailScreen()
    }
}