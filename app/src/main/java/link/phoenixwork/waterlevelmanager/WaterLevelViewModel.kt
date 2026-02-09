package link.phoenixwork.waterlevelmanager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import link.phoenixwork.waterlevelmanager.data.entity.WaterLevelEntity
import link.phoenixwork.waterlevelmanager.repo.WaterLevelRepository
import javax.inject.Inject


@HiltViewModel
class WaterLevelViewModel @Inject constructor(
    private val repo: WaterLevelRepository
) : ViewModel() {

    val levels: StateFlow<List<WaterLevelEntity>> =
        repo.observeAll()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun add(level: Int) = viewModelScope.launch {
        repo.addLevel(level)
        Log.d("shanky","added")
    }

    fun clear() = viewModelScope.launch {
        repo.clear()
    }
}