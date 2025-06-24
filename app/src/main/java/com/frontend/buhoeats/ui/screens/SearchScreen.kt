import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.viewmodel.SearchViewModel
import com.frontend.buhoeats.viewmodel.SearchViewModelFactory
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import androidx.compose.foundation.text.KeyboardActions
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.ui.theme.ThemeManager
import kotlinx.coroutines.launch

@Composable
fun Search(
    onBack: () -> Unit = {},
    onSearchResultClick: (String) -> Unit,
    navController: NavController,
    userSessionViewModel: UserSessionViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentUser = userSessionViewModel.currentUser.value
    val coroutineScope = rememberCoroutineScope()


    val searchViewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(
            userId = currentUser?.id ?: 0,
            context = context
        )
    )

    val searchHistory by searchViewModel.searchHistory.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val allRestaurants = DummyData.getRestaurants()
    val searchResults by remember(searchQuery, allRestaurants) {
        derivedStateOf {
            if (searchQuery.text.isBlank()) emptyList()
            else allRestaurants.filter { it.name.contains(searchQuery.text, ignoreCase = true) }
        }
    }

    val backgroundImage = if (ThemeManager.isDarkTheme)
        painterResource(id = R.drawable.backgrounddark)
    else
        painterResource(id = R.drawable.backgroundlighttheme)

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = onBack
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar", color = AppColors.texto) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Buscar", tint = AppColors.text)
                    },
                    shape = RoundedCornerShape(50),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.6f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                        cursorColor = AppColors.texto,
                        focusedTextColor = AppColors.texto,
                        unfocusedTextColor = AppColors.texto,
                        focusedLabelColor = AppColors.texto,
                        unfocusedLabelColor = AppColors.texto
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    trailingIcon = {
                        if (searchQuery.text.isNotBlank()) {
                            IconButton(onClick = { searchQuery = TextFieldValue("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Limpiar", tint = AppColors.text)
                            }
                        }
                    },
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchQuery.text.isNotBlank()) {
                                coroutineScope.launch {
                                    searchViewModel.addToHistory(searchQuery.text)
                                }
                                onSearchResultClick(searchQuery.text)
                            }
                        }
                    )
                )


                when {
                    searchQuery.text.isNotBlank() && searchResults.isNotEmpty() -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(searchResults) { restaurant ->
                                SearchResultItem(
                                    icon = Icons.Default.Search,
                                    text = restaurant.name,
                                    onClick = {
                                        coroutineScope.launch {
                                            searchViewModel.addToHistory(restaurant.name)
                                        }
                                        onSearchResultClick(restaurant.name)
                                    }
                                )
                            }
                        }
                    }

                    searchQuery.text.isNotBlank() && searchResults.isEmpty() -> {
                        EmptyState(message = "No hay resultados para la búsqueda",)
                    }

                    searchQuery.text.isBlank() && searchHistory.isNotEmpty() -> {
                        Column {
                            Text(
                                text = "Búsquedas recientes",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(searchHistory) { item ->
                                    SearchResultItem(
                                        icon = Icons.Default.History,
                                        text = item,
                                        onClick = { onSearchResultClick(item) },
                                        onDelete = { searchViewModel.removeFromHistory(item) }
                                    )
                                }
                            }
                        }
                    }

                    else -> {
                        EmptyState(message = "Busca restaurantes por nombre")
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchResultItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    onDelete: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 12.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        if (onDelete != null) {
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Close, contentDescription = "Eliminar")
            }
        } else {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Ir",
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

@Composable
private fun EmptyState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}