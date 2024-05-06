package com.mahor.szlaki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider


class TrailDetailFragment : Fragment() {
    private lateinit var trailViewModel: TrailViewModel
    var trailId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trail_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        trailViewModel = ViewModelProvider(this).get(TrailViewModel::class.java)
        if (savedInstanceState == null) {
            val stoper = StoperFragment()
            val ft: FragmentTransaction = childFragmentManager.beginTransaction()
            ft.add(R.id.stoper_container, stoper)
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()
        }else{
            trailId = savedInstanceState.getLong("id")
        }
    }

    override fun onStart() {
        super.onStart()
        val view = view
        if (view != null) {
            val titleTextView = view.findViewById<TextView>(R.id.textTitle)
            val descriptionTextView = view.findViewById<TextView>(R.id.textDescription)

            trailViewModel.getTrailById(trailId).observe(viewLifecycleOwner) { trail ->
                titleTextView.text = trail.name
                descriptionTextView.text = trail.description
            }
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putLong("id", trailId)
    }
}