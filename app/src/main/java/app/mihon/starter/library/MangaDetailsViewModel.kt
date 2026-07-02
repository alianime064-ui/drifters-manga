package app.mihon.starter.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mihon.starter.data.model.Chapter
import app.mihon.starter.data.model.Manga
import app.mihon.starter.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaDetailsViewModel @Inject constructor(
    private val repository: MangaRepository
) : ViewModel() {
    private val _manga = MutableStateFlow<Manga?>(null)
    val manga: StateFlow<Manga?> = _manga

    private val _chapters = MutableStateFlow<List<Chapter>>(emptyList())
    val chapters: StateFlow<List<Chapter>> = _chapters

    fun load(id: Long) {
        viewModelScope.launch {
            _manga.value = repository.getManga(id)
            _chapters.value = repository.getChapters(id)
        }
    }
}
