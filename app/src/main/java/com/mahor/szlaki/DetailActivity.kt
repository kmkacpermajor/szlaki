package com.mahor.szlaki

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class DetailActivity : AppCompatActivity() {

    val EXTRA_TRAIL_ID = "id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        val frag: TrailDetailFragment = supportFragmentManager.findFragmentById(R.id.detail_frag) as TrailDetailFragment
        val trailId = intent.getLongExtra(EXTRA_TRAIL_ID, 0)
        frag.trailId = trailId
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
