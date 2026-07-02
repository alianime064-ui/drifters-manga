package app.mihon.starter.data.repository

import app.mihon.starter.data.local.MangaDao
import app.mihon.starter.data.model.Chapter
import app.mihon.starter.data.model.HistoryEntry
import app.mihon.starter.data.model.Manga
import app.mihon.starter.data.model.MangaSource
import app.mihon.starter.data.model.UpdateEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaRepository @Inject constructor(
    private val dao: MangaDao
) {
    fun getLibrary(): Flow<List<Manga>> = dao.getLibrary()

    suspend fun getManga(id: Long): Manga? = dao.getManga(id) ?: sampleManga.find { it.id == id }

    fun getChapters(mangaId: Long): List<Chapter> {
        return (1..35).map {
            Chapter(
                id = mangaId * 100 + it,
                mangaId = mangaId,
                name = "Chapter $it - ${chapterTitles.random()}",
                chapterNumber = it.toFloat(),
                dateUpload = System.currentTimeMillis() - it * 86400000L * 3,
                read = it > 28,
                bookmark = it == 27,
                downloaded = it in 24..30,
                pagesCount = 18 + it % 7
            )
        }.reversed()
    }

    fun getHistory(): List<HistoryEntry> = sampleManga.take(8).mapIndexed { i, m ->
        HistoryEntry(
            mangaId = m.id,
            mangaTitle = m.title,
            chapterName = "Ch. ${30 - i}",
            readAt = System.currentTimeMillis() - i * 3600000L * 4,
            thumbnailUrl = m.thumbnailUrl
        )
    }

    fun getUpdates(): List<UpdateEntry> = sampleManga.shuffled().take(10).mapIndexed { i, m ->
        UpdateEntry(
            mangaId = m.id,
            mangaTitle = m.title,
            chapterName = "Chapter ${35 - i}",
            coverUrl = m.thumbnailUrl,
            updatedAt = System.currentTimeMillis() - i * 3600000L * 2
        )
    }

    fun getSources(): List<MangaSource> = listOf(
        MangaSource(1, "MangaDex", "EN", isInstalled = true),
        MangaSource(2, "MangaPlus", "EN", isInstalled = false),
        MangaSource(3, "Comick", "EN", isInstalled = true),
        MangaSource(4, "MangaFire", "EN"),
        MangaSource(5, "MangaKakalot", "EN"),
        MangaSource(6, " مانجا ليك", "AR", isInstalled = true),
        MangaSource(7, "MangaSee", "EN"),
        MangaSource(8, "Asura Scans", "EN", isNsfw = false),
    )

    suspend fun seedIfEmpty() {
        // Room prepopulate happens in DI
    }

    companion object {
        private val chapterTitles = listOf(
            "The Beginning", "Awakening", "Confrontation", "Revelation",
            "Descent", "Ascension", "Broken Oath", "Silent Night", "Crimson Sky"
        )

        val sampleManga = listOf(
            Manga(1, "Solo Leveling", "Chugong", description="Weak hunter Sung Jinwoo becomes the world's strongest.", thumbnailUrl="https://picsum.photos/seed/solo/400/600", unreadCount=3, category=0),
            Manga(2, "One Piece", "Eiichiro Oda", description="Pirate adventure to find the One Piece.", thumbnailUrl="https://picsum.photos/seed/onepiece/400/600", unreadCount=7, category=0),
            Manga(3, "Berserk", "Kentaro Miura", description="Dark fantasy epic.", thumbnailUrl="https://picsum.photos/seed/berserk/400/600", unreadCount=1, category=1),
            Manga(4, "Chainsaw Man", "Tatsuki Fujimoto", description="Devil hunter mayhem.", thumbnailUrl="https://picsum.photos/seed/chainsaw/400/600", unreadCount=0, category=0),
            Manga(5, "Jujutsu Kaisen", "Gege Akutami", description="Cursed energy battles.", thumbnailUrl="https://picsum.photos/seed/jjk/400/600", unreadCount=5, category=0),
            Manga(6, "Vagabond", "Takehiko Inoue", description="Musashi's journey.", thumbnailUrl="https://picsum.photos/seed/vagabond/400/600", unreadCount=0, category=1, trackerScore=9.5f),
            Manga(7, "Blue Lock", "Muneyuki Kaneshiro", description="Ultimate striker project.", thumbnailUrl="https://picsum.photos/seed/bluelock/400/600", unreadCount=2, category=2),
            Manga(8, "Kingdom", "Yasuhisa Hara", description="Warring states epic.", thumbnailUrl="https://picsum.photos/seed/kingdom/400/600", unreadCount=12, category=1),
            Manga(9, "Vinland Saga", "Makoto Yukimura", description="Viking revenge and redemption.", thumbnailUrl="https://picsum.photos/seed/vinland/400/600", unreadCount=0, category=1, trackerScore=9.2f),
            Manga(10, "Kaiju No. 8", "Naoya Matsumoto", description="Kaiju cleaner turned kaiju.", thumbnailUrl="https://picsum.photos/seed/kaiju/400/600", unreadCount=4, category=2),
            Manga(11, "Dandadan", "Yukinobu Tatsu", description="Aliens vs yokai chaos.", thumbnailUrl="https://picsum.photos/seed/dandadan/400/600", unreadCount=1, category=2),
            Manga(12, "Sakamoto Days", "Yuto Suzuki", description="Retired hitman family life.", thumbnailUrl="https://picsum.photos/seed/sakamoto/400/600", unreadCount=0, category=2),
        )
    }
}
