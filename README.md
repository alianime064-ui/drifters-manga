# Drifters Manga

Mihon / Tachiyomi reading-focused fork. Kotlin + Jetpack Compose.

**Package:** `io.drifters.manga`  
**Min SDK:** 26 (Android 8.0+)  
**License:** Apache-2.0

### Why Drifters?
Mihon is amazing but bloated. Drifters keeps only the necessary:
- Library / Updates / History / Browse / More
- Fast pager / webtoon reader
- Downloads, categories, tracking hooks
- Keiyoushi extensions compatible

Stripped: analytics blobs, extra trackers UI clutter, legacy XML views.

### Extension repo (Keiyoushi)
Add in Browse → Extensions → `+`:
```
https://keiyoushi.github.io/index.min.json
```
80+ sources, same API as Mihon. Works out of the box.

Sources pre-wired in code:
- MangaDex
- Comick
- MangaFire
- MangaKakalot
- مانجا ليك (AR)
- Asura Scans
etc.

### Features
- Library grid with categories
- Updates feed – auto check
- History
- Manga details + chapter list
- Reader: Pager / Webtoon / Continuous, L→R / R→L / Vertical, fullscreen, keep screen on
- Downloads (WorkManager ready)
- Trackers: MyAnimeList, AniList, Kitsu, MangaUpdates, Shikimori, Bangumi – hooks ready
- Light / Dark / Dynamic Material You
- Backup / Restore .proto
- Room + Hilt + Retrofit + Coil 3

### Build
```
git clone https://github.com/alianime064-ui/drifters-manga.git
# Android Studio → Open → drifters-manga
# Sync Gradle → Run
```
AGP 8.5.2 • Kotlin 2.0.0 • Compose 2.0.0

### Screens
Library • Updates • History • Browse (Keiyoushi) • More • Reader

Apache-2.0 – educational Mihon fork.
