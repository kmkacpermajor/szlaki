package com.mahor.szlaki

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment


class TrailListFragment : ListFragment() {
    interface Listener {
        fun itemClicked(id: Long)
    }

    lateinit var listener: Listener

    override fun onAttach(context: Context) {
        super.onAttach(requireContext())
        listener = context as Listener
    }

    override fun onListItemClick(listView: ListView, itemView: View, position: Int, id: Long) {
        listener.itemClicked(id);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val names = arrayOfNulls<String>(TrailList().trails.size)
        for (i in names.indices) {
            names[i] = TrailList().trails[i].name
        }
        val adapter: ArrayAdapter<String?> = ArrayAdapter(
            inflater.context, R.layout.simple_list_item_1, names
        )
        setListAdapter(adapter)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

}