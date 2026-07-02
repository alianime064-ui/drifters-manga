package app.mihon.starter.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavDestination(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon
) {
    data object Library : BottomNavDestination(
        "library",
        "Library",
        Icons.Outlined.CollectionsBookmark,
        Icons.Filled.CollectionsBookmark
    )
    data object Updates : BottomNavDestination(
        "updates",
        "Updates",
        Icons.Outlined.NewReleases,
        Icons.Filled.NewReleases
    )
    data object History : BottomNavDestination(
        "history",
        "History",
        Icons.Outlined.History,
        Icons.Filled.History
    )
    data object Browse : BottomNavDestination(
        "browse",
        "Browse",
        Icons.Outlined.Explore,
        Icons.Filled.Explore
    )
    data object More : BottomNavDestination(
        "more",
        "More",
        Icons.Outlined.MoreHoriz,
        Icons.Filled.MoreHoriz
    )

    companion object {
        val items = listOf(Library, Updates, History, Browse, More)
    }
}

object Routes {
    const val MANGA_DETAILS = "manga/{mangaId}"
    fun mangaDetails(mangaId: Long) = "manga/$mangaId"
    const val READER = "reader/{chapterId}"
    fun reader(chapterId: Long) = "reader/$chapterId"
    const val SETTINGS = "settings"
    const val EXTENSIONS = "extensions"
    const val DOWNLOADS = "downloads"
    const val CATEGORIES = "categories"
}
