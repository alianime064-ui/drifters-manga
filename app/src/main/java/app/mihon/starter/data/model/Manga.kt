package app.mihon.starter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
data class Manga(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val author: String = "",
    val artist: String = "",
    val description: String = "",
    val thumbnailUrl: String = "",
    val sourceId: Long = 0,
    val favorite: Boolean = true,
    val inLibrary: Boolean = true,
    val category: Int = 0,
    val unreadCount: Int = 0,
    val lastReadChapterId: Long? = null,
    val trackerScore: Float? = null // MyAnimeList / AniList integration ready
)

data class Chapter(
    val id: Long,
    val mangaId: Long,
    val name: String,
    val chapterNumber: Float,
    val dateUpload: Long,
    val read: Boolean = false,
    val bookmark: Boolean = false,
    val downloaded: Boolean = false,
    val lastPageRead: Int = 0,
    val pagesCount: Int = 20
)

data class MangaSource(
    val id: Long,
    val name: String,
    val lang: String,
    val isNsfw: Boolean = false,
    val isInstalled: Boolean = false
)

data class HistoryEntry(
    val mangaId: Long,
    val mangaTitle: String,
    val chapterName: String,
    val readAt: Long,
    val thumbnailUrl: String
)

data class UpdateEntry(
    val mangaId: Long,
    val mangaTitle: String,
    val chapterName: String,
    val coverUrl: String,
    val updatedAt: Long
)
