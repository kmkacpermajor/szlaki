package com.mahor.szlaki

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class StoperFragment(private val repository: TrailRepository, private var trailId: Long) : Fragment(), View.OnClickListener {
    private var stoperService: StoperService? = null
    private var handler: Handler? = null
    private val updateIntervalMillis: Long = 1000
    private var isRunning = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout: View = inflater.inflate(R.layout.fragment_stoper, container, false)
        val startButton = layout.findViewById<ImageButton>(R.id.start_button)
        val saveButton = layout.findViewById<ImageButton>(R.id.save_button)
        val resetButton = layout.findViewById<ImageButton>(R.id.reset_button)

        context?.let {
            stoperService = StoperService()
            val serviceIntent = Intent(it, StoperService::class.java)
            it.startService(serviceIntent)
        }

        startButton.setOnClickListener(this)
        saveButton.setOnClickListener(this)
        resetButton.setOnClickListener(this)

        // Initialize the handler for updating the timer periodically
        handler = Handler(Looper.getMainLooper())

        // Start updating the timer periodically
        startTimerUpdates()

        return layout
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Stop updating the timer periodically
        stopTimerUpdates()

        context?.let {
            val serviceIntent = Intent(it, StoperService::class.java)
            it.stopService(serviceIntent)
        }
    }

    override fun onClick(v: View) {
        stoperService?.let {
            when (v.id) {
                R.id.start_button -> {
                    if (isRunning) {
                        it.stopStoper()
                        isRunning = false
                        (v as ImageButton).setImageResource(R.drawable.ic_start)
                    } else {
                        it.startStoper()
                        isRunning = true
                        (v as ImageButton).setImageResource(R.drawable.ic_stop)
                    }
                }
                R.id.reset_button -> it.resetStoper()
                R.id.save_button -> it.saveTime(repository, trailId)
            }
        }
    }


    private fun startTimerUpdates() {
        handler?.post(object : Runnable {
            override fun run() {
                // Update the timer display
                updateTimer()

                // Schedule the next update
                handler?.postDelayed(this, updateIntervalMillis)
            }
        })
    }

    private fun stopTimerUpdates() {
        handler?.removeCallbacksAndMessages(null)
    }

    private fun updateTimer() {
        val time = stoperService?.getTime() ?: "0:00:00"
        view?.findViewById<TextView>(R.id.time_view)?.text = time
    }
}
