package com.frontend.buhoeats.data

import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.models.ContactInfo
import com.frontend.buhoeats.models.Dish
import com.frontend.buhoeats.models.Promo
import com.frontend.buhoeats.models.Review
object DummyData {

         private val users = listOf(
        User(
            id = 1,
            name = "Astrid",
            lastName = "Vides",
            imageProfile = "",
            email = "vides67@gmail.com",
            password = "astrid",
            rol = "admin"
        ),
        User(
            id = 2,
            name = "Michelle",
            lastName = "Maltez",
            imageProfile = "",
            email = "michelle@gmail.com",
            password = "michelle123",
            rol = "usuario",
            favoritos = mutableListOf(1, 2)

    ),
        User(
            id = 3,
            name = "Dayalin",
            lastName = "Hernandez",
            imageProfile = "",
            email = "dayalin@correo.com",
            password = "dayalin123",
            rol = "superadmin"
        ),
        User(
            id = 4,
            name = "Diego",
            lastName = "Alvarenga",
            imageProfile = "",
            email = "diego@hotmail.com",
            password = "diego123",
            rol = "admin"
        ),
        User(
            id = 5,
            name = "Mauricio",
            lastName = "Apellidoxd",
            imageProfile = "",
            email = "mauri@hotmail.com",
            password = "mauri123",
            rol = "usuario",
            favoritos = mutableListOf(1)
        )
    )

    fun getUsers(): List<User> {
        return users
    }

    fun getRestaurants(): List<Restaurant> {
        return listOf(
            Restaurant(
                id = 1,
                name = "Restaurante El Buen Sabor",
                description = "Comida típica salvadoreña con un toque moderno.",
                imageUrl = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
                categories = listOf("Salvadoreña", "Típica", "Familiar"),
                contactInfo = ContactInfo(
                    email = "contacto@elbuen.sv",
                    phone = "2222-3333",
                    hours = "Lunes a Domingo, 8:00 AM - 9:00 PM",
                    address = "Calle El Progreso #123, San Salvador"
                ),
                reviews = listOf(
                    Review(
                        username = "Juan Pérez",
                        comment = "Excelente atención y la comida deliciosa.",
                        rating = 5
                    ),
                    Review(
                        username = "Ana López",
                        comment = "Muy bonito el lugar y buen ambiente.",
                        rating = 4
                    )
                ),
                menu = listOf(
                    Dish(
                        id = 1,
                        name = "Pupusas revueltas",
                        description = "Pupusas con chicharrón, frijoles y queso.",
                        imageUrl = "https://images.unsplash.com/photo-1625948137139-d6672c58db6e",
                        price = "$5.00"
                    ),
                    Dish(
                        id = 2,
                        name = "Sopa de gallina india",
                        description = "Sopa tradicional con vegetales frescos.",
                        imageUrl = "https://images.unsplash.com/photo-1605478371437-045aa03f2dc1",
                        price = "$5.00"
                    )
                ),
                promos = listOf(
                    Promo(
                        id = 1,
                        name = "Pupusas de loroco",
                        description = "Pupusas con chicharrón, frijoles y queso.",
                        imageUrl = "https://images.unsplash.com/photo-1625948137139-d6672c58db6e",
                        price = "$5.00",
                        promprice = "$3.00",
                        reglas = "Solo de lunes a viernes desde las 12 pm"
                    ),
                    Promo(
                        id = 2,
                        name = "Sopa de gallina india",
                        description = "Sopa tradicional con vegetales frescos.",
                        imageUrl = "https://www.honduras.com/wp-content/uploads/2020/09/receta-sopa-de-gallina.jpg",
                        price = "$5.00",
                        promprice = "$3.00",
                        reglas = "Solo de lunes a viernes desde las 12 pm"


                    )
                ),
                latitud = 13.6929,
                longitud = -89.2182,
                admin = 1,
                blockedUsers = listOf(2)
            ),
            Restaurant(
                id = 2,
                name = "La Esquinita Gourmet",
                description = "Fusión de sabores latinos e internacionales.",
                imageUrl = "https://images.unsplash.com/photo-1600891964190-46e1c7b4cfaf",
                categories = listOf("Gourmet", "Fusión", "Internacional"),
                contactInfo = ContactInfo(
                    email = "info@laesquinita.com",
                    phone = "2444-5566",
                    hours = "Martes a Domingo, 12:00 PM - 10:00 PM",
                    address = "Avenida Central #456, Santa Tecla"
                ),
                reviews = listOf(
                    Review(
                        username = "Carlos Mendoza",
                        comment = "El mejor ceviche que he probado.",
                        rating = 4
                    ),
                    Review(
                        username = "Lucía Herrera",
                        comment = "Un poco caro, pero vale la pena.",
                        rating = 3
                    )
                ),
                menu = listOf(
                    Dish(
                        id = 3,
                        name = "Ceviche mixto",
                        description = "Pescado, camarones y calamares en jugo de limón.",
                        imageUrl = "https://images.unsplash.com/photo-1613145998174-17f89e068e2e",
                        price = "$5.00"
                    ),
                    Dish(
                        id = 4,
                        name = "Tacos al pastor",
                        description = "Tacos con carne de cerdo marinada al estilo mexicano.",
                        imageUrl = "https://images.unsplash.com/photo-1601050690094-0cfb389d7e18",
                        price = "$5.00"
                    )
                ),
                promos = listOf(
                    Promo(
                        id = 3,
                        name = "Ceviche mixto",
                        description = "Pescado, camarones y calamares en jugo de limón.",
                        imageUrl = "https://images.unsplash.com/photo-1613145998174-17f89e068e2e",
                        price = "$5.00",
                        promprice = "$3.00",
                        reglas = "Solo de lunes a viernes desde las 12 pm"

                    ),
                    Promo(
                        id = 4,
                        name = "Tacos al pastor",
                        description = "Tacos con carne de cerdo marinada al estilo mexicano.",
                        imageUrl = "https://images.unsplash.com/photo-1601050690094-0cfb389d7e18",
                        price = "$5.00",
                        promprice = "$3.00",
                        reglas = "Solo de lunes a viernes desde las 12 pm"

                    )
                ),
                latitud = 13.7929,
                longitud = -89.2182,
                admin = 4,
                blockedUsers = emptyList()
            )
        )
    }
}
