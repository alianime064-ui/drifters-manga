package app.mihon.starter.di

import android.content.Context
import androidx.room.Room
import app.mihon.starter.data.local.AppDatabase
import app.mihon.starter.data.local.MangaDao
import app.mihon.starter.data.repository.MangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mihon.db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideMangaDao(db: AppDatabase): MangaDao = db.mangaDao()

    @Provides
    @Singleton
    fun provideMangaRepository(dao: MangaDao): MangaRepository = MangaRepository(dao)
}
