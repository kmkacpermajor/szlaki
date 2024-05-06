package com.mahor.szlaki

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow

class TrailRepository(private val trailDao: TrailDao) {

    val allWords: Flow<List<Trail>> = trailDao.getAllTrails()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(trail: Trail) {
        trailDao.insert(trail)
    }

    fun getAllTrails(): LiveData<List<Trail>> {
        return trailDao.getAllTrails().asLiveData()
    }
    fun getTrailById(trailId: Long): LiveData<Trail> {
        return trailDao.getTrailById(trailId).asLiveData()
    }
}