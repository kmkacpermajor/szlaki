package com.mahor.szlaki

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class TrailListFragment : Fragment(), OnTrailItemClickListener {
    //    private val newWordActivityRequestCode = 1
    protected val trailViewModel: TrailViewModel by activityViewModels {
        TrailViewModelFactory((requireActivity().application as TrailsApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab1, container, false)
    }

    abstract fun observeTrails(adapter: TrailListAdapter)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        val adapter = TrailListAdapter(this)
        recyclerView.adapter = adapter
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager.spanCount = 4
        } else {
            gridLayoutManager.spanCount = 2
        }

        recyclerView.layoutManager = gridLayoutManager

        observeTrails(adapter)
    }

    override fun onTrailItemClick(id: Long) {
        val fragmentContainer = requireActivity().findViewById<View>(R.id.fragment_container)
        if (fragmentContainer != null) {
            val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val details = TrailDetailFragment()
            details.trailId = id
            ft.replace(R.id.fragment_container, details)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(null)
            ft.commit()
        } else {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(DetailActivity().EXTRA_TRAIL_ID, id)
            startActivity(intent)
        }
    }
}