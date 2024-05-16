package com.mahor.szlaki

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class StoperService : Service() {
    private var seconds = 0
    private var running = false
    private val binder: IBinder = LocalBinder()
    private val handler = Handler()

    inner class LocalBinder : Binder() {
        fun getService(): StoperService = this@StoperService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        startStoper()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopStoper()
    }

    fun startStoper() {
        if (!running){
            running = true
            handler.post(object : Runnable {
                override fun run() {
                    if (running) {
                        seconds++
                    }
                    handler.postDelayed(this, 1000)
                }
            })
        }
    }

    fun stopStoper() {
        running = false
        handler.removeCallbacksAndMessages(null)
    }

    fun resetStoper() {
        running = false
        handler.removeCallbacksAndMessages(null)
        seconds = 0
    }

    fun getTime(): String {
        val hours = seconds / 3600
        val minutes = seconds % 3600 / 60
        val secs = seconds % 60
        return String.format("%d:%02d:%02d", hours, minutes, secs)
    }

    fun saveTime(trailRepository: TrailRepository, trailId: Long) {
        val minutes = seconds / 60
        GlobalScope.launch(Dispatchers.IO) {
            trailRepository.getTrailById(trailId).first().let { trail ->
                val updatedTrail = trail.copy(walk_minutes = minutes)
                trailRepository.updateTrail(updatedTrail)
            }
        }
    }
}
