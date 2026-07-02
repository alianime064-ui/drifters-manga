package app.mihon.starter.extensions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class ExtensionRepo(
    val url: String,
    val name: String,
    val enabled: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtensionReposScreen(onBack: () -> Unit) {
    var repos by remember {
        mutableStateOf(listOf(
            ExtensionRepo("https://keiyoushi.github.io/index.min.json", "Keiyoushi", true),
            ExtensionRepo("https://raw.githubusercontent.com/keiyoushi/extensions/repo/index.min.json", "Keiyoushi GitHub mirror", false)
        ))
    }
    var showAddDialog by remember { mutableStateOf(false) }
    var newUrl by remember { mutableStateOf("") }
    var isRefreshing by remember { mutableStateOf(false) }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add repo") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Paste extension repo URL – same as Mihon:", style = MaterialTheme.typography.bodySmall)
                    OutlinedTextField(
                        value = newUrl,
                        onValueChange = { newUrl = it },
                        label = { Text("Repo URL") },
                        placeholder = { Text("https://keiyoushi.github.io/index.min.json") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text("Popular repos:\n• Keiyoushi: https://keiyoushi.github.io/index.min.json", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newUrl.isNotBlank()) {
                        repos = repos + ExtensionRepo(newUrl.trim(), newUrl.substringAfter("//").substringBefore("/"), true)
                        newUrl = ""
                    }
                    showAddDialog = false
                }) { Text("Add") }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false; newUrl = "" }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Extension repos", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                },
                actions = {
                    IconButton(onClick = { isRefreshing = true }) {
                        if (isRefreshing) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            LaunchedEffect(Unit) {
                                kotlinx.coroutines.delay(1200)
                                isRefreshing = false
                            }
                        } else {
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add repo")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    ListItem(
                        headlineContent = { Text("Drifters Manga – Mihon API", fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("Paste any Mihon/Tachiyomi repo URL above. Keiyoushi is pre-installed.\nSettings → Browse → Extension repos → +") },
                        leadingContent = { Icon(Icons.Default.Extension, null) },
                        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    )
                }
            }
            items(repos, key = { it.url }) { repo ->
                var enabled by remember(repo.url) { mutableStateOf(repo.enabled) }
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    ListItem(
                        headlineContent = { Text(repo.name, fontWeight = FontWeight.Medium) },
                        supportingContent = { 
                            Column {
                                Text(repo.url, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                                Text(if (enabled) "Enabled • Tap refresh to fetch extensions" else "Disabled", style = MaterialTheme.typography.labelSmall)
                            }
                        },
                        trailingContent = {
                            Row {
                                Switch(checked = enabled, onCheckedChange = { enabled = it })
                                IconButton(onClick = { repos = repos.filter { r -> r.url != repo.url } }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    )
                }
            }
            item {
                Spacer(Modifier.height(8.dp))
                Text("How to use – exactly like Mihon:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(
                    "1. Tap + → paste: https://keiyoushi.github.io/index.min.json\n" +
                    "2. Refresh\n" +
                    "3. Go to Browse → Extensions → Install\n" +
                    "4. Installed sources appear in Browse tab\n" +
                    "5. Search / Latest / Popular – same UI as Mihon",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(16.dp))
                OutlinedButton(onClick = { 
                    newUrl = "https://keiyoushi.github.io/index.min.json"
                    showAddDialog = true
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Add Keiyoushi repo")
                }
            }
        }
    }
}
