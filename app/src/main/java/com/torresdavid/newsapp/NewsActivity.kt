package com.torresdavid.newsapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class NewsActivity : AppCompatActivity() {
    private lateinit var homeLoader: ProgressBar
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_news)
        homeLoader = findViewById(R.id.homeLoader)
        recyclerView = findViewById(R.id.recyclerView)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showLoader()
    }

    private fun showLoader() {
        homeLoader.visibility = View.VISIBLE
    }
    fun hideLoader() {
        homeLoader.visibility = View.GONE
    }
}