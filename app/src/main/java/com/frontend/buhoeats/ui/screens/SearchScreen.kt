import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar

@Composable
fun Search(
    onNavigateToProfile: () -> Unit = {},
    onBack: () -> Unit = {},
    onSearchResultClick: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var searchHistory by remember { mutableStateOf(listOf<String>()) }

    val allRestaurants = DummyData.getRestaurants()
    val searchResults by remember(searchQuery) {
        derivedStateOf {
            if (searchQuery.text.isBlank()) {
                emptyList()
            } else {
                allRestaurants.filter {
                    it.name.contains(searchQuery.text, ignoreCase = true)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = onBack
            )
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.backgroundlighttheme),
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
                    label = { Text("Buscar restaurante") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                if (searchQuery.text.isNotBlank()) {
                    Text("Resultados:", style = MaterialTheme.typography.titleMedium)
                    LazyColumn {
                        items(searchResults) { restaurant ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (restaurant.name !in searchHistory) {
                                            searchHistory = listOf(restaurant.name) + searchHistory
                                        }
                                        onSearchResultClick(restaurant.name)
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = restaurant.name, modifier = Modifier.weight(1f))
                                Icon(Icons.Default.ArrowForward, contentDescription = "Ir")
                            }
                        }
                    }
                } else {
                    LazyColumn {
                        items(searchHistory) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSearchResultClick(item) }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = "Historial",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(text = item, modifier = Modifier.weight(1f))
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Ir"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
