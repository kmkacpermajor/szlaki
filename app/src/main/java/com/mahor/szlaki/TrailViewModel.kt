package com.mahor.szlaki

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TrailViewModel(private val repository: TrailRepository) : ViewModel() {

    val allWords: LiveData<List<Trail>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(trail: Trail) = viewModelScope.launch {
        repository.insert(trail)
    }

    fun getTrailById(trailId: Long): LiveData<Trail> {
        return repository.getTrailById(trailId)
    }
}
