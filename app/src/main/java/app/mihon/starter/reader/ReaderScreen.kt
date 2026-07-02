package app.mihon.starter.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(onBack: () -> Unit) {
    var showUi by remember { mutableStateOf(true) }
    val pageCount = 22
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .clickable { showUi = !showUi }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                // Placeholder manga page – real app loads from source / cache
                AsyncImage(
                    model = "https://picsum.photos/seed/manga${page * 13}/800/1200",
                    contentDescription = "Page ${page + 1}",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    "Page ${page + 1} / $pageCount",
                    color = Color.White.copy(alpha = 0.35f),
                    modifier = Modifier.align(Alignment.BottomCenter).padding(24.dp)
                )
            }
        }

        if (showUi) {
            TopAppBar(
                title = { Text("Ch. 35", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Settings, contentDescription = "Reader settings", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.5f)
                )
            )
        }

        if (showUi) {
            Surface(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                color = Color.Black.copy(alpha = 0.6f),
                tonalElevation = 0.dp
            ) {
                Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${pagerState.currentPage + 1} / $pageCount", color = Color.White, fontWeight = FontWeight.Medium)
                    Slider(
                        value = (pagerState.currentPage + 1).toFloat(),
                        onValueChange = {},
                        valueRange = 1f..pageCount.toFloat(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextButton(onClick = {}) { Text("← Prev", color = Color.White) }
                        Text("Pager • L→R", color = Color.White.copy(0.8f))
                        TextButton(onClick = {}) { Text("Next →", color = Color.White) }
                    }
                }
            }
        }
    }
}
