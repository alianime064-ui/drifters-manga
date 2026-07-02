package app.mihon.starter.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.mihon.starter.browse.BrowseScreen
import app.mihon.starter.history.HistoryScreen
import app.mihon.starter.library.LibraryScreen
import app.mihon.starter.library.MangaDetailsScreen
import app.mihon.starter.more.MoreScreen
import app.mihon.starter.more.SettingsScreen
import app.mihon.starter.reader.ReaderScreen
import app.mihon.starter.updates.UpdatesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MihonNavHost() {
    val navController = rememberNavController()
    val bottomNavRoutes = BottomNavDestination.items.map { it.route }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = currentDestination?.hierarchy?.any { it.route in bottomNavRoutes } == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    BottomNavDestination.items.forEach { screen ->
                        val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            icon = { Icon(if (selected) screen.selectedIcon else screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavDestination.Library.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavDestination.Library.route) {
                LibraryScreen(
                    onMangaClick = { mangaId ->
                        navController.navigate(Routes.mangaDetails(mangaId))
                    }
                )
            }
            composable(BottomNavDestination.Updates.route) {
                UpdatesScreen()
            }
            composable(BottomNavDestination.History.route) {
                HistoryScreen()
            }
            composable(BottomNavDestination.Browse.route) {
                BrowseScreen()
            }
            composable(BottomNavDestination.More.route) {
                MoreScreen(
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
            composable(
                route = Routes.MANGA_DETAILS,
                arguments = listOf(navArgument("mangaId") { type = NavType.LongType })
            ) { backStackEntry ->
                val mangaId = backStackEntry.arguments?.getLong("mangaId") ?: 0L
                MangaDetailsScreen(
                    mangaId = mangaId,
                    onBack = { navController.popBackStack() },
                    onReadClick = { chapterId ->
                        navController.navigate(Routes.reader(chapterId))
                    }
                )
            }
            composable(
                route = Routes.READER,
                arguments = listOf(navArgument("chapterId") { type = NavType.LongType })
            ) {
                ReaderScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Routes.SETTINGS) {
                SettingsScreen(onBack = { navController.popBackStack() })
            }
            composable(Routes.EXTENSIONS) {
                ExtensionsPlaceholder(onBack = { navController.popBackStack() })
            }
            composable(Routes.DOWNLOADS) {
                DownloadsPlaceholder(onBack = { navController.popBackStack() })
            }
            composable(Routes.CATEGORIES) {
                CategoriesPlaceholder(onBack = { navController.popBackStack() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExtensionsPlaceholder(onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Extensions") }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
        })
    }) { pad ->
        Surface(modifier = Modifier.padding(pad)) {
            BrowseScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DownloadsPlaceholder(onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Downloads") }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
        })
    }) { pad ->
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(pad).fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Download queue – ready for WorkManager integration", modifier = Modifier.padding())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoriesPlaceholder(onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Categories") }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
        })
    }) { pad ->
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(pad).fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Edit categories – drag & drop ready")
        }
    }
}
