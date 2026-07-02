package app.mihon.starter.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.mihon.starter.data.repository.MangaRepository
import app.mihon.starter.updates.UpdatesViewModel
import coil3.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(viewModel: UpdatesViewModel = hiltViewModel()) {
    val history = remember { viewModel.repo.getHistory() }
    val sdf = remember { SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History", fontWeight = FontWeight.SemiBold) },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.DeleteSweep, contentDescription = "Clear")
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
            items(history) { h ->
                ListItem(
                    headlineContent = { Text(h.mangaTitle) },
                    supportingContent = { Text("${h.chapterName} • ${sdf.format(Date(h.readAt))}") },
                    leadingContent = {
                        Card(modifier = Modifier.size(56.dp, 56.dp)) {
                            AsyncImage(
                                model = h.thumbnailUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    }
}
