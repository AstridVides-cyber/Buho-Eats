package com.frontend.buhoeats.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


object Translations {
    enum class Language { ES, EN }

    var currentLanguage by mutableStateOf(Language.ES)

    private val strings = mapOf(
        "my_account" to mapOf(Language.ES to "Mi cuenta", Language.EN to "My Account"),
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
        "error_password_mismatch" to mapOf(Language.ES to "Las contraseñas no coinciden", Language.EN to "Passwords do not match"),
        "edit_restaurant_info" to mapOf(Language.ES to "Editar Información del Restaurante", Language.EN to "Edit Restaurant Info"),
        "email" to mapOf(Language.ES to "Correo", Language.EN to "Email"),
        "phone" to mapOf(Language.ES to "Teléfono", Language.EN to "Phone"),
        "schedule" to mapOf(Language.ES to "Horario", Language.EN to "Schedule"),
        "address" to mapOf(Language.ES to "Dirección", Language.EN to "Address"),
        "error_email_required" to mapOf(Language.ES to "El correo no puede estar vacío", Language.EN to "Email cannot be empty"),
        "error_email_invalid" to mapOf(Language.ES to "El formato del correo no es válido", Language.EN to "Invalid email format"),
        "error_phone_required" to mapOf(Language.ES to "El teléfono no puede estar vacío", Language.EN to "Phone number cannot be empty"),
        "error_phone_invalid" to mapOf(Language.ES to "El formato del teléfono no es válido", Language.EN to "Invalid phone number format"),
        "error_schedule_required" to mapOf(Language.ES to "El horario no puede estar vacío", Language.EN to "Schedule cannot be empty"),
        "error_address_required" to mapOf(Language.ES to "La dirección no puede estar vacía", Language.EN to "Address cannot be empty"),
        "cancel" to mapOf(Language.ES to "Cancelar", Language.EN to "Cancel"),
        "confirm" to mapOf(Language.ES to "Confirmar", Language.EN to "Confirm"),
        "info_updated_success" to mapOf(Language.ES to "Información editada exitosamente", Language.EN to "Information successfully updated"),
        "edit_restaurant_image" to mapOf(
            Translations.Language.ES to "Editar Imagen del Restaurante",
            Translations.Language.EN to "Edit Restaurant Image"
        ),
        "selected_image" to mapOf(
            Translations.Language.ES to "Imagen seleccionada",
            Translations.Language.EN to "Selected image"
        ),
        "current_image" to mapOf(
            Translations.Language.ES to "Imagen actual del restaurante",
            Translations.Language.EN to "Current restaurant image"
        ),
        "change_image" to mapOf(
            Translations.Language.ES to "Cambiar imagen",
            Translations.Language.EN to "Change image"
        ),
        "save_changes" to mapOf(
            Translations.Language.ES to "Guardar cambios",
            Translations.Language.EN to "Save changes"
        ),
        "image_saved_successfully" to mapOf(
            Translations.Language.ES to "Imagen guardada correctamente",
            Translations.Language.EN to "Image saved successfully"
        ),
        "add_location" to mapOf(Translations.Language.ES to "Agregar local", Translations.Language.EN to "Add restaurant"),
        "edit_location" to mapOf(Translations.Language.ES to "Editar local", Translations.Language.EN to "Edit restaurant"),
        "local_image" to mapOf(Translations.Language.ES to "Imagen del local", Translations.Language.EN to "Restaurant image"),
        "change_image" to mapOf(Translations.Language.ES to "Cambiar imagen", Translations.Language.EN to "Change image"),
        "name" to mapOf(Translations.Language.ES to "Nombre", Translations.Language.EN to "Name"),
        "name_r_placeholder" to mapOf(Translations.Language.ES to "Ej. Pupusería La Esperanza", Translations.Language.EN to "e.g. La Esperanza Diner"),
        "name_required" to mapOf(Translations.Language.ES to "El nombre no puede estar vacío", Translations.Language.EN to "Name cannot be empty"),
        "description" to mapOf(Translations.Language.ES to "Descripción:", Translations.Language.EN to "Description:"),
        "description_placeholder" to mapOf(Translations.Language.ES to "Breve descripción del local", Translations.Language.EN to "Brief description of the place"),
        "description_required" to mapOf(Translations.Language.ES to "La descripción no puede estar vacía", Translations.Language.EN to "Description cannot be empty"),
        "category" to mapOf(Translations.Language.ES to "Categoría:", Translations.Language.EN to "Category:"),
        "select_category" to mapOf(Translations.Language.ES to "Selecciona una categoría", Translations.Language.EN to "Select a category"),
        "admin_email" to mapOf(Translations.Language.ES to "Administrador:", Translations.Language.EN to "Administrator:"),
        "admin_placeholder" to mapOf(Translations.Language.ES to "Correo del administrador", Translations.Language.EN to "Administrator's email"),
        "invalid_email" to mapOf(Translations.Language.ES to "Correo no válido", Translations.Language.EN to "Invalid email"),
        "invalid_admin_role" to mapOf(Translations.Language.ES to "El correo debe pertenecer a un administrador", Translations.Language.EN to "Email must belong to an admin"),
        "invalid_location" to mapOf(Translations.Language.ES to "Selecciona una ubicación válida", Translations.Language.EN to "Select a valid location"),
        "confirm_add_local" to mapOf(Translations.Language.ES to "¿Estás seguro que deseas agregar un nuevo local?", Translations.Language.EN to "Are you sure you want to add a new restaurant?"),
        "confirm_edit_local" to mapOf(Translations.Language.ES to "¿Deseas guardar los cambios en el local?", Translations.Language.EN to "Do you want to save the changes to the restaurant?"),
        "location_created" to mapOf(Translations.Language.ES to "Local creado exitosamente", Translations.Language.EN to "Restaurant created successfully"),
        "breakfast" to mapOf(Language.ES to "Desayuno", Language.EN to "Breakfast"),
        "lunch" to mapOf(Language.ES to "Almuerzo", Language.EN to "Lunch"),
        "dinner" to mapOf(Language.ES to "Cena", Language.EN to "Dinner"),
        "select_location" to mapOf(
            Language.ES to "Selecciona la ubicación en el mapa",
            Language.EN to "Select the location on the map"
        ),
        "local_location" to mapOf(
            Language.ES to "Ubicación del local",
            Language.EN to "Location"
        ),
        "edit_daily_menu" to mapOf(Language.ES to "Editar Menú del día:", Language.EN to "Edit Daily Menu"),
        "dish_name" to mapOf(Language.ES to "Nombre del plato:", Language.EN to "Dish Name:"),
        "dish_name_placeholder" to mapOf(Language.ES to "Ej. Pupusas revueltas", Language.EN to "e.g. Cheese Pupusas"),
        "error_name_required" to mapOf(Language.ES to "El nombre no puede estar vacío", Language.EN to "Name cannot be empty"),

        "description" to mapOf(Language.ES to "Descripción:", Language.EN to "Description:"),
        "dish_description_placeholder" to mapOf(Language.ES to "Breve descripción del plato", Language.EN to "Brief dish description"),
        "error_description_required" to mapOf(Language.ES to "La descripción no puede estar vacía", Language.EN to "Description cannot be empty"),

        "price" to mapOf(Language.ES to "Precio:", Language.EN to "Price:"),
        "price_placeholder" to mapOf(Language.ES to "Ej. 2.50", Language.EN to "e.g. 2.50"),
        "error_price_required" to mapOf(Language.ES to "El precio no puede estar vacío.", Language.EN to "Price cannot be empty."),
        "error_price_format" to mapOf(Language.ES to "El precio solo debe contener números", Language.EN to "Price must contain only numbers"),

        "cancel" to mapOf(Language.ES to "Cancelar", Language.EN to "Cancel"),
        "confirm" to mapOf(Language.ES to "Confirmar", Language.EN to "Confirm"),
        "dish_added_success" to mapOf(Language.ES to "Plato agregado exitosamente", Language.EN to "Dish added successfully"),
        "edit_image" to mapOf(Language.ES to "Editar Imagen", Language.EN to "Edit Image"),
        "edit_menu" to mapOf(Language.ES to "Editar Menú", Language.EN to "Edit Menu"),
        "edit_info" to mapOf(Language.ES to "Editar Información", Language.EN to "Edit Info"),
        "no_favorites_message" to mapOf(
            Language.ES to "No has añadido ningún restaurante a favoritos.",
            Language.EN to "You haven't added any restaurants to favorites yet."
        ),
        "welcome_user" to mapOf(
            Language.ES to "Bienvenido,",
            Language.EN to "Welcome,"
        ),
        "your_location" to mapOf(
            Language.ES to "Su local:",
            Language.EN to "Your restaurant:"
        ),
        "restaurants_title" to mapOf(
            Language.ES to "Restaurantes",
            Language.EN to "Restaurants"
        ),
        "no_restaurant_images" to mapOf(
            Language.ES to "No hay imágenes de restaurantes disponibles.",
            Language.EN to "No restaurant images available."
        ),
        "breakfast" to mapOf(Language.ES to "Desayuno", Language.EN to "Breakfast"),
        "lunch" to mapOf(Language.ES to "Almuerzo", Language.EN to "Lunch"),
        "dinner" to mapOf(Language.ES to "Cena", Language.EN to "Dinner"),
        "results" to mapOf(Language.ES to "Resultados:", Language.EN to "Results:"),
        "reset" to mapOf(Language.ES to "Restablecer", Language.EN to "Reset"),
        "confirm_delete_restaurant" to mapOf(
            Language.ES to "¿Seguro que deseas eliminar el local?",
            Language.EN to "Are you sure you want to delete this restaurant?"
        ),
        "add_restaurant" to mapOf(
            Language.ES to "Agregar nuevo restaurante",
            Language.EN to "Add new restaurant"
        ),
        "email_label" to mapOf(Language.ES to "Correo:", Language.EN to "Email:"),
        "email_placeholder" to mapOf(Language.ES to "Ingrese su correo", Language.EN to "Enter your email"),
        "password_label" to mapOf(Language.ES to "Contraseña:", Language.EN to "Password:"),
        "password_placeholder" to mapOf(Language.ES to "Ingrese su contraseña", Language.EN to "Enter your password"),
        "login_button" to mapOf(Language.ES to "Iniciar sesión", Language.EN to "Log in"),
        "login_with_google" to mapOf(Language.ES to "Inicia Sesión con Google", Language.EN to "Sign in with Google"),
        "no_account_question" to mapOf(Language.ES to "¿Todavía no tienes cuenta?", Language.EN to "Don't have an account yet?"),
        "create_account" to mapOf(Language.ES to "Crear cuenta", Language.EN to "Create account"),
        "error_email_required" to mapOf(Language.ES to "El correo no puede estar vacío", Language.EN to "Email cannot be empty"),
        "error_email_invalid" to mapOf(Language.ES to "Correo inválido", Language.EN to "Invalid email"),
        "error_password_required" to mapOf(Language.ES to "La contraseña no puede estar vacía", Language.EN to "Password cannot be empty"),
        "error_credentials" to mapOf(Language.ES to "Correo o contraseña incorrectos", Language.EN to "Incorrect email or password"),
        "login_success" to mapOf(Language.ES to "Sesión iniciada con éxito", Language.EN to "Login successful"),
        "login_google_success" to mapOf(Language.ES to "Sesión iniciada con Google", Language.EN to "Logged in with Google"),
        "user_not_found" to mapOf(Language.ES to "Usuario no encontrado, por favor regístrate", Language.EN to "User not found, please sign up"),
        "google_signin_error" to mapOf(Language.ES to "Error en Google Sign-In", Language.EN to "Google Sign-In Error"),
        "logout" to mapOf(Language.ES to "Cerrar Sesión", Language.EN to "Log Out"),
        "logout_confirmation" to mapOf(Language.ES to "¿Estás seguro que deseas cerrar sesión?", Language.EN to "Are you sure you want to log out?"),
        "edit_profile" to mapOf(Language.ES to "Editar perfil", Language.EN to "Edit profile"),
        "confirm" to mapOf(Language.ES to "Confirmar", Language.EN to "Confirm"),
        "cancel" to mapOf(Language.ES to "Cancelar", Language.EN to "Cancel"),
        "promotion" to mapOf(Translations.Language.ES to "Promoción", Translations.Language.EN to "Promotion"),
        "title" to mapOf(Translations.Language.ES to "Título:", Translations.Language.EN to "Title:"),
        "before" to mapOf(Translations.Language.ES to "Antes:", Translations.Language.EN to "Before:"),
        "now" to mapOf(Translations.Language.ES to "Ahora:", Translations.Language.EN to "Now:"),
        "description" to mapOf(Translations.Language.ES to "Descripción:", Translations.Language.EN to "Description:"),
        "rules" to mapOf(Translations.Language.ES to "Reglas:", Translations.Language.EN to "Rules:"),
        "no_rules" to mapOf(Translations.Language.ES to "Sin reglas específicas", Translations.Language.EN to "No specific rules"),
        "only_numbers_allowed" to mapOf(Translations.Language.ES to "Solo números permitidos", Translations.Language.EN to "Only numbers allowed"),
        "fill_all_fields" to mapOf(Translations.Language.ES to "Por favor completa todos los campos", Translations.Language.EN to "Please complete all fields"),
        "numbers_only_error" to mapOf(Translations.Language.ES to "Los precios solo deben contener números", Translations.Language.EN to "Prices must only contain numbers"),
        "cancel" to mapOf(Translations.Language.ES to "Cancelar", Translations.Language.EN to "Cancel"),
        "save" to mapOf(Translations.Language.ES to "Guardar", Translations.Language.EN to "Save"),
        "promo_created" to mapOf(Translations.Language.ES to "Promoción creada exitosamente", Translations.Language.EN to "Promotion created successfully"),
        "promo_saved" to mapOf(Translations.Language.ES to "Promoción guardada exitosamente", Translations.Language.EN to "Promotion saved successfully"),
        "change_image" to mapOf(Translations.Language.ES to "Cambiar imagen", Translations.Language.EN to "Change image"),
        "selected_image" to mapOf(Translations.Language.ES to "Imagen seleccionada", Translations.Language.EN to "Selected image"),
        "current_image" to mapOf(Translations.Language.ES to "Imagen actual", Translations.Language.EN to "Current image"),
        "loading_promotions" to mapOf(Translations.Language.ES to "Cargando promociones...", Translations.Language.EN to "Loading promotions..."),
        "no_promotions_yet" to mapOf(Translations.Language.ES to "Tu restaurante aún no tiene promociones.", Translations.Language.EN to "Your restaurant has no promotions yet."),
        "no_promotions_available" to mapOf(Translations.Language.ES to "No hay promociones disponibles en este momento.", Translations.Language.EN to "No promotions available at the moment."),
        "promotions_title" to mapOf(Translations.Language.ES to "Promociones", Translations.Language.EN to "Promotions"),
        "promotion_deleted" to mapOf(Translations.Language.ES to "Promoción eliminada", Translations.Language.EN to "Promotion deleted"),

        "no_reviews" to mapOf(
            Language.ES to "No hay reseñas aún en el restaurante",
            Language.EN to "There are no reviews yet for this restaurant"
        ),
        "unknown_user" to mapOf(
            Language.ES to "Usuario desconocido",
            Language.EN to "Unknown user"
        ),
        "block_user_confirm" to mapOf(
            Language.ES to "¿Estás seguro que deseas bloquear a este usuario?",
            Language.EN to "Are you sure you want to block this user?"
        ),
        "user_blocked_success" to mapOf(
            Language.ES to "Usuario bloqueado exitosamente",
            Language.EN to "User successfully blocked"
        ),

        "create_account" to mapOf(Language.ES to "Crear cuenta", Language.EN to "Create account"),
        "register_with_google" to mapOf(Language.ES to "Regístrate con Google", Language.EN to "Register with Google"),
        "already_have_account" to mapOf(Language.ES to "¿Ya tienes una cuenta?", Language.EN to "Already have an account?"),
        "login" to mapOf(Language.ES to "Iniciar sesión", Language.EN to "Log in"),
        "register" to mapOf(Language.ES to "Registrarte", Language.EN to "Register"),
        "google_register_success" to mapOf(Language.ES to "Cuenta Google registrada con éxito", Language.EN to "Google account successfully registered"),
        "google_register_error" to mapOf(Language.ES to "Error al iniciar sesión con Google", Language.EN to "Error signing in with Google"),
        "account_exists" to mapOf(Language.ES to "Ya existe una cuenta con este correo", Language.EN to "An account with this email already exists"),
        "account_created_success" to mapOf(Language.ES to "Cuenta creada con éxito", Language.EN to "Account successfully created"),
        "email_in_use" to mapOf(Language.ES to "Este correo ya está en uso", Language.EN to "This email is already in use"),
        "confirm_password_required" to mapOf(Language.ES to "Debe confirmar la contraseña", Language.EN to "You must confirm the password"),

        "search" to mapOf(Language.ES to "Buscar", Language.EN to "Search"),
        "clear" to mapOf(Language.ES to "Limpiar", Language.EN to "Clear"),
        "no_results_found" to mapOf(Language.ES to "No hay resultados para la búsqueda", Language.EN to "No results found"),
        "recent_searches" to mapOf(Language.ES to "Búsquedas recientes", Language.EN to "Recent searches"),
        "search_hint" to mapOf(Language.ES to "Busca restaurantes por nombre", Language.EN to "Search restaurants by name"),
        "delete" to mapOf(Language.ES to "Eliminar", Language.EN to "Delete"),
        "go" to mapOf(Language.ES to "Ir", Language.EN to "Go"),

        "assign_roles_title" to mapOf(Language.ES to "Asignar Roles", Language.EN to "Assign Roles"),
        "email_label" to mapOf(Language.ES to "Correo:", Language.EN to "Email:"),
        "email_placeholder_admin" to mapOf(Language.ES to "Ingrese el correo del usuario", Language.EN to "Enter user's email"),
        "role_type_label" to mapOf(Language.ES to "Tipo de Rol:", Language.EN to "Role Type:"),
        "select_role" to mapOf(Language.ES to "Seleccione un rol", Language.EN to "Select a role"),
        "select_role_error" to mapOf(Language.ES to "Seleccione un rol", Language.EN to "Please select a role"),
        "invalid_email" to mapOf(Language.ES to "Ingrese un correo válido", Language.EN to "Enter a valid email"),
        "only_superadmins_can_assign" to mapOf(Language.ES to "Solo los Super Administradores pueden asignar roles", Language.EN to "Only Super Admins can assign roles"),
        "user_not_found" to mapOf(Language.ES to "No se encontró ningún usuario con ese correo", Language.EN to "No user found with that email"),
        "role_assigned_success" to mapOf(Language.ES to "Rol asignado exitosamente", Language.EN to "Role assigned successfully"),
        "confirm" to mapOf(Language.ES to "Confirmar", Language.EN to "Confirm"),

        "role_superadmin" to mapOf(Language.ES to "Super Administrador", Language.EN to "Super Admin"),
        "role_admin" to mapOf(Language.ES to "Administrador de Local", Language.EN to "Local Admin"),
        "role_user" to mapOf(Language.ES to "Usuario", Language.EN to "User"),

        "rateApp" to mapOf(
            Language.ES to "Califica la app",
            Language.EN to "Rate the app"
        ),
        "writeOpinion" to mapOf(
            Language.ES to "Escribe tu opinión:",
            Language.EN to "Write your opinion:"
        ),
        "submitReview" to mapOf(
            Language.ES to "Publicar",
            Language.EN to "Submit"
        ),
        "alreadyRated" to mapOf(
            Language.ES to "Ya calificaste este restaurante con %d estrellas.",
            Language.EN to "You already rated this restaurant %d stars."
        ),
        "blockedMessage" to mapOf(
            Language.ES to "Has sido bloqueado de este restaurante y no puedes dejar opiniones.",
            Language.EN to "You have been blocked from this restaurant and cannot leave reviews."
        ),
        "ratingsAndComments" to mapOf(
            Language.ES to "Calificaciones y opiniones",
            Language.EN to "Ratings and comments"
        ),
        "deleteDishConfirmation" to mapOf(
            Language.ES to "¿Estás seguro que deseas eliminar el plato?",
            Language.EN to "Are you sure you want to delete the dish?"
        ),
        "deleteDishSuccess" to mapOf(
            Language.ES to "Plato eliminado correctamente",
            Language.EN to "Dish deleted successfully"
        ),
        "selectRatingError" to mapOf(
            Language.ES to "Por favor, selecciona una calificación (estrellas) antes de publicar.",
            Language.EN to "Please select a rating (stars) before submitting."
        ),
        "emptyCommentError" to mapOf(
            Language.ES to "Por favor, escribe tu opinión antes de publicar.",
            Language.EN to "Please write your opinion before submitting."
        ),
        "dailyMenu" to mapOf(
            Language.ES to "Menú del día",
            Language.EN to "Today's Menu"
        ),
        "favoritesAdd" to mapOf(
            Language.ES to "Agregar a favoritos",
            Language.EN to "Add to favorites"
        ),
        "favoritesRemove" to mapOf(
            Language.ES to "Quitar de favoritos",
            Language.EN to "Remove from favorites"
        ),

        "confirmationMessage" to mapOf(
            Language.ES to "¿Estás seguro que deseas desbloquear a este usuario?",
            Language.EN to "Are you sure you want to unblock this user?"
        ),

        "light_mode" to mapOf(
            Language.ES to "Modo claro",
            Language.EN to "Light mode"
        ),
        "dark_mode" to mapOf(
            Language.ES to "Modo oscuro",
            Language.EN to "Dark mode"
        ),

        "contact_email" to mapOf(
            Language.ES to "Correo",
            Language.EN to "Email"
        ),
        "contact_phone" to mapOf(
            Language.ES to "Teléfono",
            Language.EN to "Phone"
        ),
        "contact_hours" to mapOf(
            Language.ES to "Horario",
            Language.EN to "Hours"
        ),
        "contact_address" to mapOf(
            Language.ES to "Dirección",
            Language.EN to "Address"
        ),
        "unknown_user" to mapOf(
            Language.ES to "Usuario desconocido",
            Language.EN to "Unknown user"
        ),
        "star_description" to mapOf(
            Language.ES to "Estrella",
            Language.EN to "Star"
        ),

        "location_permission_request" to mapOf(
            Language.ES to "Solicitando permiso de ubicación...",
            Language.EN to "Requesting location permission..."
        ),
        "location_loading" to mapOf(
            Language.ES to "Cargando ubicación...",
            Language.EN to "Loading location..."
        ),

        "you_are_here" to mapOf(
            Language.ES to "Tú estás aquí",
            Language.EN to "You are here"
        ),

        "language_spanish" to mapOf(
            Language.ES to "Español",
            Language.EN to "Spanish"
        ),
        "language_english" to mapOf(
            Language.ES to "Inglés",
            Language.EN to "English"
        )


    )




    fun t(key: String): String {
        return strings[key]?.get(currentLanguage) ?: key
    }
}