package com.mahor.szlaki

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TrailsApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { TrailRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { TrailRepository(database.trailDao()) }
}