package app.mihon.starter.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mihon.starter.data.local.MangaDao
import app.mihon.starter.data.model.Manga
import app.mihon.starter.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LibraryUiState(
    val manga: List<Manga> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: MangaRepository,
    private val dao: MangaDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            // seed database on first launch
            try {
                dao.insertAll(MangaRepository.sampleManga)
            } catch (_: Exception) {}
        }
        repository.getLibrary()
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .catch { 
                // fallback to sample
                _uiState.update { 
                    it.copy(manga = MangaRepository.sampleManga, isLoading = false)
                }
            }
            .onEach { list ->
                val data = if (list.isEmpty()) MangaRepository.sampleManga else list
                _uiState.update { it.copy(manga = data, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }
}
