package com.mahor.szlaki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TrailViewModel(private val repository: TrailRepository) : ViewModel() {

    val allTrails: LiveData<List<Trail>> = repository.allTrails.asLiveData()
    val easyTrails: LiveData<List<Trail>> = repository.easyTrails.asLiveData()
    val mediumTrails: LiveData<List<Trail>> = repository.mediumTrails.asLiveData()
    val hardTrails: LiveData<List<Trail>> = repository.hardTrails.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(trail: Trail) = viewModelScope.launch {
        repository.insert(trail)
    }

    fun getTrailById(id: Long): LiveData<Trail> {
        return repository.getTrailById(id).asLiveData()
    }

    fun updateTrail(trail: Trail) = viewModelScope.launch {
        repository.updateTrail(trail)
    }
}
