# Mihon Clone – Android Manga Reader Starter

Modern open-source manga reader starter inspired by **Mihon / Tachiyomi** – ready to open in Android Studio and hit Run.

Built: July 2026
Package: `app.mihon.starter`
Min SDK: 26 (Android 8.0+) – matches real Mihon [1](https://alternativeto.net/software/mihon/about/)
Target SDK: 34
Language: **Kotlin + Jetpack Compose + Material 3**

### Features included (Mihon-parity scaffold)

- Library grid with categories / tabs – “Categories to organize your library” [1](https://alternativeto.net/software/mihon/about/)
- Updates feed – “Schedule updating your library for new chapters” [1](https://alternativeto.net/software/mihon/about/)
- History screen
- Browse / Extensions screen – “Online reading from a variety of sources” [1](https://alternativeto.net/software/mihon/about/) – extension system stub ready
- Manga details + chapter list (read / download / bookmark states)
- **Configurable reader** – Pager / Webtoon / Continuous modes – “a customizable reader with multiple viewers and settings” [1](https://alternativeto.net/software/mihon/about/)
- Light and dark themes [1](https://alternativeto.net/software/mihon/about/)
- Tracker hooks ready: MyAnimeList, AniList, Kitsu, MangaUpdates, Shikimori, Bangumi [2](https://sourceforge.net/projects/mihon.mirror/)
- Local reading of downloaded content [3](https://www.openapk.net/mihon/app.mihon/)
- Backup & Restore UI (.proto ready)
- MVVM + Hilt + Room + Retrofit + Coil 3 (Mihon migrated to Coil 3)
- Downloads queue placeholder (WorkManager ready)

> Mihon is “Free and open source manga reader for Android. Discover and read manga, webtoons, comics, and more” [3](https://www.openapk.net/mihon/app.mihon/), successor to Tachiyomi, Apache-2.0 licensed.

### Open in Android Studio

1. Download / unzip `MihonClone` folder
2. Android Studio: **File → Open → select MihonClone**
3. Let Gradle sync – AGP 8.5.2, Kotlin 2.0.0, Compose Compiler 2.0.0
4. Run on device / emulator (Android 8.0+)

If Gradle wrapper jar is missing:
`File → Settings → Build Tools → Gradle → Use Gradle wrapper` – AS will download `gradle-8.7-bin`.

### Project structure

```
app/src/main/java/app/mihon/starter/
  MainActivity.kt
  MihonApp.kt
  navigation/
    MihonNavHost.kt  (5 bottom tabs: Library, Updates, History, Browse, More)
  library/
    LibraryScreen.kt + LibraryViewModel.kt
    MangaDetailsScreen.kt
  reader/
    ReaderScreen.kt  (HorizontalPager, L→R / R→L / Vertical ready)
  browse/
    BrowseScreen.kt  (Extension source list)
  updates/
  history/
  more/
    SettingsScreen.kt
  data/
    model/Manga.kt
    local/AppDatabase.kt / MangaDao.kt
    repository/MangaRepository.kt
  di/AppModule.kt
  ui/theme/
```

### Next steps (real Mihon wiring)

- Extension API: add Keiyoushi extension loader
- Source catalog: Retrofit + OkHttp interceptors
- Chapter page loader: Coil 3 + disk cache + DownloadManager
- Tracker OAuth: MAL, AniList, Kitsu
- Backup: protobuf export matching Mihon .tachibk
- Reader: 2-page spread, crop borders, color filter, long-tap menu

Apache-2.0 – educational starter, not affiliated with mihon.app

Enjoy!
