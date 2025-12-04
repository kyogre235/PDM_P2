# PDM_P3 - Pokédex Android

Proyecto final de Programación de Dispositivos Móviles.

Aplicación Android que funciona como una **Pokédex** (enciclopedia de Pokémon). Esta aplicación mejora el codigo previo entregado en la tarea 3:

## Features

- Esta app utiliza el api publica PokeApi (https://pokeapi.co/api/v2/) para conseguir los datos de los Pokemon (esta limitado a solo los primeros 151 Pokemon).
- Tambien muestra los distintos objetos que se le pueden equipar a los pokemon
- Posee la capacidad de realizar busquedas mediante el nombre de un objeto o de un Pokemon
Además, se realizaron algunas mejoras visuales para una mejor la experiencia del usuario.

## Estructura

Un cambio importante con respecto a la Tarea 3 es la refactorizacion del codigo, ahora la estructura del proyecto luce asi:

```
app
├── manifests
├── kotlin+java
│   ├── com.example.pokedex
│   │   ├── adapter
│   │   │   ├── ItemAdapter.kt
│   │   │   └── PokemonAdapter.kt
│   │   ├── db
│   │   │   ├── Converters.kt
│   │   │   ├── ItemDao.kt
│   │   │   ├── PokemonDao.kt
│   │   │   └── PokemonDatabase.kt
│   │   ├── lector
│   │   │   └── PokeApi.kt
│   │   ├── model
│   │   │   ├── Ability.kt
│   │   │   ├── Item.kt
│   │   │   └── Pokemon.kt
│   │   ├── repositorio
│   │   │   ├── ItemRepository.kt
│   │   │   └── PokemonRepository.kt
│   │   ├── settings
│   │   │   └── SettingsFragment.kt
│   │   ├── ui
│   │   │   ├── item
│   │   │   └── pokedex
│   │   ├── viewModel
│   │   │   ├── ItemViewModel.kt
│   │   │   ├── ItemViewModelFactory.kt
│   │   │   ├── PokemonViewModel.kt
│   │   │   └── PokemonViewModelFactory.kt
│   │   └── MainActivity.kt
│   ├── com.example.pokedex (androidTest)
│   └── com.example.pokedex (test)
├── java (generated)
├── res
└── res (generated)
```
### adapter

En la carpeta adapter, se guardan los adapter que nos permiten transformar nuestros datos crudos de la base de datos, a un elemento que puede ser mostrado como lista en el fragmento correspondiente.

### db

Aqui estan los archivos que se encargan de gestionar el acceso a la base de datos, tanto en esritura como en lectura.

### lector

Aqui se gestiona la llamada a la PokeApi.

### model

Aqui se guardan los modelos del proyecto, son basicamente las plantillas que nos dicen que datos lleva cada objeto a guardar/leer en la BD.

### repositorio

Aqui se gestiona la conexion entre los la base de datos y la propia aplicacion.

### settings

Aqui se gestionan los fragmentos que se requieran en el menu de configuracion.

### ui

Aqui se gestionan los fragmentos de la interfaz de usuario (sin contar settings).

### viewmodel

Aqui se gestionan los viewmodel que utilizan los framentos para mostrar la informacion.

## Integrantes
- Karla Sheridam Guadalupe Abrego
- Jacome Delgado Alejandro
