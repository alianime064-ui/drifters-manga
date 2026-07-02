package app.mihon.starter.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.mihon.starter.navigation.Routes

data class MoreItem(val title: String, val subtitle: String, val icon: ImageVector, val route: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(onNavigate: (String) -> Unit) {
    val items = listOf(
        MoreItem("Downloads", "Queue & storage", Icons.Default.Download, Routes.DOWNLOADS),
        MoreItem("Categories", "Organize your library", Icons.Default.Category, Routes.CATEGORIES),
        MoreItem("Backup & restore", "Local & cloud", Icons.Default.Backup, Routes.SETTINGS),
        MoreItem("Extensions", "Manage sources", Icons.Default.Extension, Routes.EXTENSIONS),
        MoreItem("Migrate", "Move between sources", Icons.Default.SwapHoriz, Routes.SETTINGS),
        MoreItem("Settings", "Reader, library, tracking", Icons.Default.Settings, Routes.SETTINGS),
        MoreItem("About", "Drifters Manga v0.1.0", Icons.Default.Info, Routes.SETTINGS),
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("More", fontWeight = FontWeight.SemiBold) })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    ListItem(
                        headlineContent = { Text("Drifters Manga", fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("Open-source manga reader • Apache-2.0\nTrackers: MAL • AniList • Kitsu • MangaUpdates") },
                        leadingContent = { Icon(Icons.Default.AutoStories, null, modifier = Modifier.size(32.dp)) },
                        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    )
                }
                Spacer(Modifier.height(12.dp))
            }
            items(items.size) { i ->
                val it = items[i]
                ListItem(
                    headlineContent = { Text(it.title) },
                    supportingContent = { Text(it.subtitle) },
                    leadingContent = { Icon(it.icon, contentDescription = null) },
                    modifier = Modifier.clickable { onNavigate(it.route) }
                )
                HorizontalDivider()
            }
        }
    }
}
