package com.mahor.szlaki

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction


class DetailActivity : AppCompatActivity() {

    val EXTRA_TRAIL_ID = "id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        val frag: TrailDetailFragment = supportFragmentManager.findFragmentById(R.id.detail_frag) as TrailDetailFragment
        val trailId = intent.getLongExtra(EXTRA_TRAIL_ID, 0)
        frag.trailId = trailId
    }
}