package app.mihon.starter.browse

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.mihon.starter.updates.UpdatesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseScreen(viewModel: UpdatesViewModel = hiltViewModel()) {
    val sources = remember { viewModel.repo.getSources() }
    var showRepoDialog by remember { mutableStateOf(false) }

    if (showRepoDialog) {
        AlertDialog(
            onDismissRequest = { showRepoDialog = false },
            title = { Text("Extension repos") },
            text = { 
                Column {
                    Text("Keiyoushi (Mihon official fork extensions):", fontWeight = FontWeight.Bold)
                    Text("https://keiyoushi.github.io/index.min.json", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(8.dp))
                    Text("Drifters Manga uses the same extension API as Mihon/Tachiyomi. Add the repo above in Extensions > + button.")
                    Spacer(Modifier.height(8.dp))
                    Text("Installed: MangaDex, Comick, مانجا ليك")
                }
            },
            confirmButton = {
                TextButton(onClick = { showRepoDialog = false }) { Text("Close") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Browse", fontWeight = FontWeight.SemiBold) },
                actions = {
                    IconButton(onClick = { showRepoDialog = true }) {
                        Icon(Icons.Default.TravelExplore, contentDescription = "Global search")
                    }
                    IconButton(onClick = { showRepoDialog = true }) {
                        Icon(Icons.Default.Extension, contentDescription = "Extensions")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    onClick = { showRepoDialog = true }
                ) {
                    ListItem(
                        headlineContent = { Text("Extensions • Keiyoushi", fontWeight = FontWeight.SemiBold) },
                        supportingContent = { Text("Tap to add repo: keiyoushi.github.io\nDrifters Manga – Mihon API compatible") },
                        leadingContent = { Icon(Icons.Default.Extension, null) },
                        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    )
                }
            }
            item {
                Text("Installed", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(vertical = 8.dp))
            }
            items(sources.filter { it.isInstalled }) { s ->
                SourceItem(s.name, s.lang, "Tap to browse • Latest • Popular • Keiyoushi")
            }
            item {
                Spacer(Modifier.height(12.dp))
                Text("Extension Repo", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(vertical = 8.dp))
                OutlinedCard(modifier = Modifier.fillMaxWidth(), onClick = { showRepoDialog = true }) {
                    ListItem(
                        headlineContent = { Text("keiyoushi.github.io") },
                        supportingContent = { Text("Official Mihon extension repo – 80+ sources\nEN • AR • ES • FR • RU • JP") }
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text("Available – ${sources.size} sources", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(vertical = 8.dp))
            }
            items(sources.filter { !it.isInstalled }) { s ->
                SourceItem(s.name, s.lang, "Not installed – Tap to install from Keiyoushi")
            }
            item {
                Spacer(Modifier.height(24.dp))
                Text(
                    "Drifters Manga v0.2\nMihon/Tachiyomi Extension API compatible\nApache-2.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SourceItem(name: String, lang: String, subtitle: String) {
    ListItem(
        headlineContent = { Text(name, fontWeight = FontWeight.Medium) },
        supportingContent = { Text(subtitle) },
        leadingContent = {
            AssistChip(onClick = {}, label = { Text(lang) })
        },
        trailingContent = {
            Icon(Icons.Default.Language, contentDescription = null)
        }
    )
    HorizontalDivider()
}
