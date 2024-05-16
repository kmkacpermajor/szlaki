package com.mahor.szlaki

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrailRepository(private val trailDao: TrailDao) {

    val allTrails: Flow<List<Trail>> = trailDao.getAllTrails()
    val easyTrails: Flow<List<Trail>> = trailDao.getDifficultyTrails("easy")
    val mediumTrails: Flow<List<Trail>> = trailDao.getDifficultyTrails("medium")
    val hardTrails: Flow<List<Trail>> = trailDao.getDifficultyTrails("hard")

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(trail: Trail) {
        trailDao.insert(trail)
    }

    fun getTrailById(id: Long): Flow<Trail> {
        return trailDao.getTrailById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateTrail(trail: Trail) {
        trailDao.update(trail)
    }
}