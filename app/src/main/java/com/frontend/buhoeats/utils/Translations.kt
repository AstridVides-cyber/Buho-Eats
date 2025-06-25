package com.frontend.buhoeats.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


object Translations {
    enum class Language { ES, EN }

    var currentLanguage by mutableStateOf(Language.ES)

    private val strings = mapOf(
        "my_account" to mapOf(Language.ES to "Mi cuenta", Language.EN to "My Account"),
        "mode" to mapOf(Language.ES to "Modo", Language.EN to "Mode"),
        "light" to mapOf(Language.ES to "Claro", Language.EN to "Light"),
        "dark" to mapOf(Language.ES to "Oscuro", Language.EN to "Dark"),
        "language" to mapOf(Language.ES to "Idioma", Language.EN to "Language"),
        "favorites" to mapOf(Language.ES to "Favoritos", Language.EN to "Favorites"),
        "statistics" to mapOf(Language.ES to "Ver estadísticas", Language.EN to "View Statistics"),
        "blocked_users" to mapOf(Language.ES to "Clientes bloqueados", Language.EN to "Blocked Users"),
        "assign_roles" to mapOf(Language.ES to "Asignar roles a un usuario", Language.EN to "Assign roles to a user"),
        "blocked_users_title" to mapOf(Language.ES to "Usuarios bloqueados:", Language.EN to "Blocked Users:"),
        "no_blocked_users" to mapOf(Language.ES to "No hay usuarios bloqueados por ahora", Language.EN to "There are no blocked users at the moment"),
        "user_unblocked" to mapOf(Language.ES to "Usuario desbloqueado exitosamente", Language.EN to "User successfully unblocked"),
        "no_permission" to mapOf(Language.ES to "No tienes permisos para ver esta pantalla", Language.EN to "You do not have permission to view this screen"),
        "home" to mapOf(Language.ES to "Inicio", Language.EN to "Home"),
        "promotions" to mapOf(Language.ES to "Promociones", Language.EN to "Promotions"),
        "map" to mapOf(Language.ES to "Mapa", Language.EN to "Map"),
        "search" to mapOf(Language.ES to "Buscar", Language.EN to "Search")

    )

    fun t(key: String): String {
        return strings[key]?.get(currentLanguage) ?: key
    }
}