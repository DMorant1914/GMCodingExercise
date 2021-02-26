package com.example.codingexcercise.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codingexcercise.R
import com.example.codingexcercise.model.data.MusicItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout.view.*

class MusicAdapter(var dataSet: List<MusicItem>, val musicSelector: MusicSelector)
    : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {


    interface MusicSelector {
        fun selectTrack(track: MusicItem)
    }
   inner class MusicViewHolder(val musicView: View) :
        RecyclerView.ViewHolder(musicView) {

        fun onBind(dataItem: MusicItem ){


            Picasso.get().load(dataItem.artworkUrl100).into(musicView.iv_cover)
            musicView.findViewById<TextView>(R.id.tv_songTitle).text = "Song: " + dataItem.trackName
            musicView.findViewById<TextView>(R.id.tv_artist).text = "Artist: " + dataItem.artistName
            musicView.findViewById<TextView>(R.id.tv_genre).text ="Genre: " + dataItem.primaryGenreName
            musicView.findViewById<TextView>(R.id.tv_release).text ="Release Date: " + dataItem.releaseDate
            musicView.findViewById<TextView>(R.id.tv_price).text = "Price: $"+ dataItem.trackPrice.toString()



            itemView.setOnClickListener {
                musicSelector.selectTrack(dataItem)

            }
        }
    }

    fun updateMusicList(dataSet: List<MusicItem>){
        this.dataSet = dataSet
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.onBind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}