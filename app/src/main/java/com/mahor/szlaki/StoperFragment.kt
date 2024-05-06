package com.mahor.szlaki

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class StoperFragment : Fragment(), View.OnClickListener {
    private var stoperService: StoperService? = null
    private var handler: Handler? = null
    private val updateIntervalMillis: Long = 1000 // Update timer every second

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout: View = inflater.inflate(R.layout.fragment_stoper, container, false)
        val startButton = layout.findViewById<Button>(R.id.start_button)
        val stopButton = layout.findViewById<Button>(R.id.stop_button)
        val resetButton = layout.findViewById<Button>(R.id.reset_button)
        val timeView = layout.findViewById<TextView>(R.id.time_view)

        context?.let {
            stoperService = StoperService()
            val serviceIntent = Intent(it, StoperService::class.java)
            it.startService(serviceIntent)
        }

        startButton.setOnClickListener(this)
        stopButton.setOnClickListener(this)
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
                R.id.start_button -> it.startStoper()
                R.id.stop_button -> it.stopStoper()
                R.id.reset_button -> it.resetStoper()
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
