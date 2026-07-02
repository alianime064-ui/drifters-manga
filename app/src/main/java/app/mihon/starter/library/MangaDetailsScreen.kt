package app.mihon.starter.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.mihon.starter.data.model.Chapter
import app.mihon.starter.data.model.Manga
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaDetailsScreen(
    mangaId: Long,
    onBack: () -> Unit,
    onReadClick: (Long) -> Unit,
    viewModel: MangaDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(mangaId) { viewModel.load(mangaId) }
    val manga by viewModel.manga.collectAsState()
    val chapters by viewModel.chapters.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(manga?.title ?: "Manga") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.FavoriteBorder, null) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Download, null) }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { chapters.firstOrNull()?.let { onReadClick(it.id) } },
                icon = { Icon(Icons.Default.PlayArrow, null) },
                text = { Text("Start") }
            )
        }
    ) { padding ->
        manga?.let { m ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(bottom = 88.dp)
            ) {
                item {
                    DetailsHeader(m)
                }
                item {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    ListItem(
                        headlineContent = { Text("${chapters.size} chapters", fontWeight = FontWeight.SemiBold) },
                        supportingContent = { Text("Swipe • Long-press multi-select • Download supported") }
                    )
                }
                items(chapters) { ch ->
                    ChapterRow(ch, onClick = { onReadClick(ch.id) })
                }
            }
        } ?: Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun DetailsHeader(manga: Manga) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(modifier = Modifier.size(130.dp, 185.dp)) {
            AsyncImage(
                model = manga.thumbnailUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(manga.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(manga.author, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (manga.trackerScore != null) {
                AssistChip(onClick = {}, label = { Text("★ ${manga.trackerScore} AniList") })
            }
            Text(
                manga.description.ifEmpty { "Drifters Manga – Mihon/Tachiyomi fork, stripped reading-focused. Supports extensions, trackers (MyAnimeList, AniList, Kitsu), categories, downloads, and a configurable reader." },
                style = MaterialTheme.typography.bodySmall
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = true, onClick = {}, label = { Text("In library") })
                FilterChip(selected = false, onClick = {}, label = { Text("Tracking") })
            }
        }
    }
}

@Composable
private fun ChapterRow(ch: Chapter, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(ch.name, maxLines = 1, color = if (ch.read) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface) },
        supportingContent = {
            Text(
                "${if (ch.read) "Read • " else ""}${ch.pagesCount} pages • ${
                    java.text.SimpleDateFormat("MMM dd", java.util.Locale.getDefault())
                        .format(java.util.Date(ch.dateUpload))
                }"
            )
        },
        trailingContent = {
            Row {
                if (ch.downloaded) {
                    Icon(Icons.Default.Download, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(8.dp))
                }
                if (ch.bookmark) {
                    Icon(Icons.Default.BookmarkBorder, contentDescription = null)
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .clickable { onClick() },
        colors = ListItemDefaults.colors(
            containerColor = if (ch.read) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    )
    HorizontalDivider()
}
