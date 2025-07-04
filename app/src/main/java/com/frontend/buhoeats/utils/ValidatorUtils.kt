package com.frontend.buhoeats.utils

object ValidatorUtils {
    fun isOnlyLetters(text: String): Boolean {
        return text.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isSecurePassword(password: String): Boolean {
        // Al menos 1 minúscula, 1 mayúscula, 1 número, 1 símbolo y mínimo 8 caracteres
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$")
        return password.matches(regex)
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("^[+]?[0-9\\s-]+$"))
    }

    fun capitalizeWords(input: String): String {
        return input.split(" ").joinToString(" ") { it.lowercase().replaceFirstChar { c -> c.uppercaseChar() } }
    }

    fun isValidPrice(text: String): Boolean {
        return text.matches(Regex("^\\d+(\\.\\d{1,2})?$"))
    }
}
