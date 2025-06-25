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
        "search" to mapOf(Language.ES to "Buscar", Language.EN to "Search"),
        "edit_account_title" to mapOf(Language.ES to "Mi cuenta", Language.EN to "My Account"),
        "name" to mapOf(Language.ES to "Nombre", Language.EN to "Name"),
        "name_placeholder" to mapOf(Language.ES to "Ingrese su nombre", Language.EN to "Enter your name"),
        "lastname" to mapOf(Language.ES to "Apellido", Language.EN to "Last name"),
        "lastname_placeholder" to mapOf(Language.ES to "Ingrese su apellido", Language.EN to "Enter your last name"),
        "email" to mapOf(Language.ES to "Correo", Language.EN to "Email"),
        "email_placeholder" to mapOf(Language.ES to "Ingrese su correo", Language.EN to "Enter your email"),
        "password" to mapOf(Language.ES to "Contraseña", Language.EN to "Password"),
        "password_placeholder" to mapOf(Language.ES to "Ingrese su contraseña", Language.EN to "Enter your password"),
        "confirm_password" to mapOf(Language.ES to "Confirmar contraseña", Language.EN to "Confirm password"),
        "confirm_password_placeholder" to mapOf(Language.ES to "Repita su contraseña", Language.EN to "Repeat your password"),
        "cancel" to mapOf(Language.ES to "Cancelar", Language.EN to "Cancel"),
        "confirm" to mapOf(Language.ES to "Confirmar", Language.EN to "Confirm"),
        "user_updated" to mapOf(Language.ES to "Usuario actualizado correctamente", Language.EN to "User updated successfully"),
        "error_name_required" to mapOf(Language.ES to "El nombre no debe estar vacío", Language.EN to "Name cannot be empty"),
        "error_name_letters" to mapOf(Language.ES to "El nombre solo debe contener letras", Language.EN to "Name must contain only letters"),
        "error_lastname_required" to mapOf(Language.ES to "El apellido no debe estar vacío", Language.EN to "Last name cannot be empty"),
        "error_lastname_letters" to mapOf(Language.ES to "El apellido solo debe contener letras", Language.EN to "Last name must contain only letters"),
        "error_email_required" to mapOf(Language.ES to "El correo no debe estar vacío", Language.EN to "Email cannot be empty"),
        "error_email_invalid" to mapOf(Language.ES to "Correo inválido", Language.EN to "Invalid email"),
        "error_password_security" to mapOf(Language.ES to "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo", Language.EN to "Password must be at least 8 characters long and include uppercase, lowercase, number, and symbol"),
        "error_password_mismatch" to mapOf(Language.ES to "Las contraseñas no coinciden", Language.EN to "Passwords do not match")


    )

    fun t(key: String): String {
        return strings[key]?.get(currentLanguage) ?: key
    }
}