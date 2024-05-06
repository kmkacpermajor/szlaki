package com.mahor.szlaki

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahor.szlaki.Trail


class CaptionedImagesAdapter(private val trails: List<Trail>) : RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>() {
    private lateinit var cardView: CardView
    lateinit var listener: Listener

    interface Listener {
        fun onClick(position: Int)
    }

    class ViewHolder(v: CardView) : RecyclerView.ViewHolder(v) {
        val cardView: CardView

        init {
            cardView = v
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cv = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_captioned_image, parent, false) as CardView
        return ViewHolder(cv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView: CardView = holder.cardView
        cardView.setOnClickListener {
            listener.onClick(position)
        }
        val imageView = cardView.findViewById<View>(R.id.info_image) as ImageView
        Glide.with(cardView.context)
            .load(trails[position].photoUrl)
            .into(imageView)
        imageView.contentDescription = trails[position].description
        val textView = cardView.findViewById<View>(R.id.info_text) as TextView
        textView.text = trails[position].name
    }

    override fun getItemCount(): Int {
        return trails.size
    }

}
