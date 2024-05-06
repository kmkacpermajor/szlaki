package com.mahor.szlaki

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TrailListFragment : ListFragment() {
    private lateinit var trailRepository: TrailRepository
    interface Listener {
        fun itemClicked(id: Long)
    }

    lateinit var listener: Listener

    override fun onAttach(context: Context) {
        super.onAttach(requireContext())
        listener = context as Listener
        trailRepository = (requireActivity().application as TrailsApplication).repository
    }

    override fun onListItemClick(listView: ListView, itemView: View, position: Int, id: Long) {
        listener.itemClicked(id);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val trails = trailRepository.getAllTrails()

        val recyclerView =
            inflater.inflate(R.layout.fragment_tab1, container, false) as RecyclerView
        val adapter = CaptionedImagesAdapter()
        adapter.listener = (object : CaptionedImagesAdapter.Listener {
            override fun onClick(position: Int) {
                val intent = Intent(
                    activity,
                    DetailActivity::class.java
                )
                intent.putExtra("id", position)
                activity!!.startActivity(intent)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)


        return super.onCreateView(inflater, container, savedInstanceState)
    }

}