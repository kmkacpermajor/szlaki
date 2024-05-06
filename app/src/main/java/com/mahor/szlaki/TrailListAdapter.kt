//package com.mahor.szlaki
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//
//class TrailListAdapter : ListAdapter<Trail, TrailListAdapter.TrailViewHolder>(TrailsComparator()) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailViewHolder {
//        return TrailViewHolder.create(parent)
//    }
//
//    override fun onBindViewHolder(holder: TrailViewHolder, position: Int) {
//        val current = getItem(position)
//        holder.bind(current.name)
//    }
//
//    class TrailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val trailItemView: TextView = itemView.findViewById(R.id.textView)
//
//        fun bind(text: String?) {
//            trailItemView.text = text
//        }
//
//        companion object {
//            fun create(parent: ViewGroup): TrailViewHolder {
//                val view: View = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.recyclerview_item, parent, false)
//                return TrailViewHolder(view)
//            }
//        }
//    }
//
//    class TrailsComparator : DiffUtil.ItemCallback<Trail>() {
//        override fun areItemsTheSame(oldItem: Trail, newItem: Trail): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Trail, newItem: Trail): Boolean {
//            return oldItem.name == newItem.name
//        }
//    }
//}
package com.mahor.szlaki

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TrailListAdapter(private val trails: List<Trail>) : ListAdapter<Trail, TrailListAdapter.ViewHolder>(TrailsComparator()) {
    lateinit var listener: Listener

    interface Listener {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_captioned_image, parent, false) as CardView
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trail = getItem(position)
        holder.bind(trail)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val linearLayout: LinearLayout = itemView.findViewById(R.id.linear_layout)
        private val imageView: ImageView = itemView.findViewById(R.id.info_image)
        private val textView: TextView = itemView.findViewById(R.id.info_text)

        init {
            linearLayout.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }

        fun bind(trail: Trail) {
            Glide.with(itemView.context)
                .load(trail.photoUrl)
                .into(imageView)
            textView.text = trail.name
            imageView.contentDescription = trail.description
        }
    }

    class TrailsComparator : DiffUtil.ItemCallback<Trail>() {
        override fun areItemsTheSame(oldItem: Trail, newItem: Trail): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Trail, newItem: Trail): Boolean {
            return oldItem.name == newItem.name && oldItem.photoUrl == newItem.photoUrl
        }
    }

    override fun getItemCount(): Int {
        return trails.size
    }
}
