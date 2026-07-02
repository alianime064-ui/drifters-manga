package app.mihon.starter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.mihon.starter.data.model.Manga
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {
    @Query("SELECT * FROM manga WHERE inLibrary = 1 ORDER BY title ASC")
    fun getLibrary(): Flow<List<Manga>>

    @Query("SELECT * FROM manga WHERE id = :id")
    suspend fun getManga(id: Long): Manga?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mangas: List<Manga>)

    @Query("DELETE FROM manga")
    suspend fun clear()
}
