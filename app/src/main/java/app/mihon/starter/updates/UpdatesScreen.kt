package app.mihon.starter.updates

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.mihon.starter.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel

@HiltViewModel
class UpdatesViewModel @Inject constructor(
    val repo: MangaRepository
) : ViewModel()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatesScreen(viewModel: UpdatesViewModel = hiltViewModel()) {
    val updates = remember { viewModel.repo.getUpdates() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Updates", fontWeight = FontWeight.SemiBold) },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Refresh, contentDescription = "Check updates")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(updates) { u ->
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    ListItem(
                        headlineContent = { Text(u.mangaTitle, fontWeight = FontWeight.Medium) },
                        supportingContent = { Text(u.chapterName) },
                        leadingContent = {
                            Card(modifier = Modifier.size(48.dp)) {
                                coil3.compose.AsyncImage(
                                    model = u.coverUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
