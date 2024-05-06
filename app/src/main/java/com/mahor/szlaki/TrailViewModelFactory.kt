package com.mahor.szlaki

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TrailViewModelFactory(private val repository: TrailRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}