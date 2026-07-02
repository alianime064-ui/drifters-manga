package app.mihon.starter.data.backup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

/**
 * Drifters Manga – Mihon-compatible backup restore
 * 
 * SAFETY: Mihon backup schema includes tracker/stats/notes fields.
 * We STRIP them gracefully – never crash on unknown fields.
 * 
 * See fork spec checklist:
 * "Local backup export + restore round-trips without crashing (verify
 *  after tracker/stats/notes removal — these fields may exist in the
 *  backup schema and need to be safely ignored/stripped, not left dangling)"
 */
object BackupManager {
    private val moshi = Moshi.Builder()
        // KotlinJsonAdapterFactory removed – using codegen only
        // .add(KotlinJsonAdapterFactory())
        // IMPORTANT: do NOT .failOnUnknown() – we WANT to ignore tracker/stats/notes
        .build()

    @JsonClass(generateAdapter = true)
    data class BackupManga(
        val title: String,
        val author: String? = null,
        val artist: String? = null,
        val description: String? = null,
        val url: String,
        val source: Long,
        // Tracker fields from Mihon – explicitly ignored but parsed safely if present
        @Json(name = "tracking") val tracking: List<Map<String, Any?>>? = null,
        // Stats / notes – stripped
        @Json(name = "notes") val notes: String? = null,
        val categories: List<Int> = emptyList(),
        val chapters: List<BackupChapter> = emptyList(),
        // any other unknown fields are ignored by Moshi
    )

    @JsonClass(generateAdapter = true)
    data class BackupChapter(
        val url: String,
        val name: String,
        val read: Boolean = false,
        val bookmark: Boolean = false,
        val lastPageRead: Int = 0,
        // tracker/stats fields stripped safely
    )

    @JsonClass(generateAdapter = true)
    data class BackupRoot(
        val manga: List<BackupManga> = emptyList(),
        val categories: List<Map<String, Any>> = emptyList(),
        // tracker/stats/notes top-level stripped automatically
        val version: Int = 2
    )

    fun restore(json: String): Result<BackupRoot> = runCatching {
        val adapter = moshi.adapter(BackupRoot::class.java).lenient()
        adapter.fromJson(json) ?: BackupRoot()
    }

    fun exportSample(): String {
        val adapter = moshi.adapter(BackupRoot::class.java).indent("  ")
        return adapter.toJson(BackupRoot())
    }
}
