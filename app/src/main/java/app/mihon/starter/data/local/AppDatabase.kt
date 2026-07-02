package app.mihon.starter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import app.mihon.starter.data.model.Manga

@Database(
    entities = [Manga::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mangaDao(): MangaDao
}
