package com.mahor.szlaki

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity(), TrailListFragment.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }

    override fun itemClicked(id: Long) {
        val fragmentContainer = findViewById<View>(R.id.fragment_container)
        if (fragmentContainer != null){
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            var details = TrailDetailFragment()
            details.trailId = id
            ft.replace(R.id.fragment_container, details)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(null)
            ft.commit()
        }else{
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity().EXTRA_TRAIL_ID, id)
            startActivity(intent)
        }

    }
}