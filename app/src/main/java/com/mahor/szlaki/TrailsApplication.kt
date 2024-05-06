package com.mahor.szlaki

import android.app.Application

class TrailsApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { TrailDatabase.getDatabase(this) }
    val repository by lazy { TrailRepository(database.trailDao()) }
}