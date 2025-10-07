# PDM_P2
Tarea 2 de dispositivos moviles
Para esta tarea se hicieron las vistas para una aplicacion que funcionara como pokedex (enciclopledia de pokemon) la primera vista tiene el listado de los pokemon (solo estan cargados 3 pero esta diseñado para que se muestren todos)
la seguna vista es la informacion mas a detalle del pokemon seleccionado (mas a adelante se conectan)

## Integrantes
- Karla Sheridam Guadalupe Abrego
- Jacome Delgado Alejandro

> nota, para ver la vista 2, se debe cambiar una linea en el archivo `app/src/main/res/navigation/nav_graph.xml`, esta linea es:
> - `app:startDestination="@id/FirstFragment">` para la primer vista 
> - `app:startDestination="@id/PokemonDetailFragment">` para ver la segunda vista

pues en activity main se llama a este archivo para definir la ruta de navegacion.

