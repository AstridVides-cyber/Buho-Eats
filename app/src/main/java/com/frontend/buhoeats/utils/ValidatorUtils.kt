    package com.frontend.buhoeats.utils

    object ValidatorUtils {
        fun isOnlyLetters(text: String): Boolean {
            return text.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
        }

        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }