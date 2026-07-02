package app.mihon.starter.more

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var readerMode by remember { mutableStateOf("Pager") }
    var direction by remember { mutableStateOf("Left to Right") }
    var keepScreenOn by remember { mutableStateOf(true) }
    var fullscreen by remember { mutableStateOf(true) }
    var updateInterval by remember { mutableStateOf(12f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Reader", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Card { Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Reading mode: $readerMode")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Pager", "Webtoon", "Continuous").forEach {
                            FilterChip(selected = readerMode == it, onClick = { readerMode = it }, label = { Text(it) })
                        }
                    }
                    Text("Direction: $direction")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("L → R", "R → L", "Vertical").forEach {
                            FilterChip(selected = direction.contains(it.split(" ")[0]), onClick = {}, label = { Text(it) })
                        }
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Keep screen on")
                        Switch(checked = keepScreenOn, onCheckedChange = { keepScreenOn = it })
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Fullscreen")
                        Switch(checked = fullscreen, onCheckedChange = { fullscreen = it })
                    }
                } }
            }
            item {
                Text("Library", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Card { Column(Modifier.padding(16.dp)) {
                    Text("Auto update interval: ${updateInterval.toInt()}h")
                    Slider(value = updateInterval, onValueChange = { updateInterval = it }, valueRange = 0f..48f, steps = 7)
                    Text("Categories • Download management • Tracking – MyAnimeList / AniList / Kitsu ready", style = MaterialTheme.typography.bodySmall)
                } }
            }
            item {
                Text("Downloads", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Card { Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Download directory: /Mihon/downloads")
                    Text("Remove after read • Only on Wi-Fi • Concurrent: 3", style = MaterialTheme.typography.bodySmall)
                } }
            }
            item {
                Text("Backup", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Card { Column(Modifier.padding(16.dp)) {
                    Button(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Create backup (.proto)") }
                    Spacer(Modifier.height(8.dp))
                    OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Restore backup") }
                } }
            }
            item {
                Text("About – Drifters Manga 0.1.0\nApache-2.0 • Kotlin + Compose\nInspired by Mihon / Tachiyomi", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}
