package com.example.codingexcercise.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.codingexcercise.R
import com.example.codingexcercise.model.data.MusicItem
import com.example.codingexcercise.view.adapter.MusicAdapter
import com.example.codingexcercise.viewmodel.MusicViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Date
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), MusicAdapter.MusicSelector {

    private val viewModel: MusicViewModel by viewModels()

    private val adapter = MusicAdapter(mutableListOf(), this)

    private lateinit var searchEditText: EditText
    private lateinit var button: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        searchEditText = findViewById(R.id.searchText)

        viewModel.musicLiveData.observe(this, Observer {
            Log.d("TAG_X", "result obtained.")
            displayMusic(it)
        })

        viewModel.errorData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
        })

        button = findViewById(R.id.button)

        button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val search = searchText.text.toString()
            searchText.text.clear()
            viewModel.getMusic(search)

        }

        recyclerView = findViewById(R.id.music_recyclerview)
        recyclerView.adapter = adapter
    }


    private fun displayMusic(music: List<MusicItem>) {
        adapter.updateMusicList(music)
        progressBar.visibility = View.GONE
    }

    fun PlayPreview(data: MusicItem) {

        val url = data.previewUrl
        val preview = Intent(Intent.ACTION_VIEW)
        preview.data = Uri.parse(url)
        startActivity(preview)

    }

    override fun selectTrack(track: MusicItem) {
        PlayPreview(track)
        Toast.makeText(this, track.trackName, Toast.LENGTH_SHORT).show()

    }
}