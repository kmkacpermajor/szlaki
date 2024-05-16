package com.mahor.szlaki

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.coroutines.coroutineContext


class TrailListAdapter(private val listener: OnTrailItemClickListener) :
    ListAdapter<Trail, TrailListAdapter.TrailViewHolder>(TrailsComparator()), Filterable {

    private var trailListFull: List<Trail> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailViewHolder {
        return TrailViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: TrailViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.id.toLong(), current.name, current.photoUrl)

        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.in_recycler)
        holder.itemView.startAnimation(animation)
    }

    class TrailViewHolder(itemView: View, private val listener: OnTrailItemClickListener) : RecyclerView.ViewHolder(itemView) {
        private val trailItemView: TextView = itemView.findViewById(R.id.info_text)
        private val trailPhotoView: ImageView = itemView.findViewById(R.id.info_image)

        fun bind(id: Long, text: String, url: String) {
            trailItemView.text = text
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
            Glide.with(itemView).load(url).apply(options).into(trailPhotoView)

            itemView.setOnClickListener {
                listener.onTrailItemClick(id)
            }
        }

        companion object {
            fun create(parent: ViewGroup, listener: OnTrailItemClickListener): TrailViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return TrailViewHolder(view, listener)
            }
        }
    }

    class TrailsComparator : DiffUtil.ItemCallback<Trail>() {
        override fun areItemsTheSame(oldItem: Trail, newItem: Trail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Trail, newItem: Trail): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun getFilter(): Filter {
        return trailFilter
    }

    private val trailFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<Trail> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(trailListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (item in trailListFull) {
                    if (item.name.toLowerCase().contains(filterPattern) ||
                        item.description.toLowerCase().contains(filterPattern)
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            submitList(results.values as List<Trail>)
        }
    }

    fun setTrailList(trailList: List<Trail>) {
        trailListFull = ArrayList(trailList)
        submitList(trailList)
    }
}
