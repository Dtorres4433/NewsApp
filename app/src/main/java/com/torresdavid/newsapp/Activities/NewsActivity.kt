package com.torresdavid.newsapp.Activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.torresdavid.newsapp.Adapter.NewsAdapter
import com.torresdavid.newsapp.Adapter.RetrofitAdapter
import com.torresdavid.newsapp.Classes.MediaStack
import com.torresdavid.newsapp.Classes.News
import com.torresdavid.newsapp.Interface.ApiService
import com.torresdavid.newsapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {
    private lateinit var homeLoader: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var chipGroup: ChipGroup
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
        getNews()
    }

    private fun getNews() {
        val retrofitAdapter = RetrofitAdapter()
        val apiService = retrofitAdapter.getRetrofit().create(ApiService::class.java)
        val call = apiService.getNews()
        call.enqueue(object : Callback<MediaStack> {
            override fun onResponse(call: Call<MediaStack>, response: Response<MediaStack>) {
                if (response.code() == 200) {
                    val mediaStack = response.body()
                    loadNews(mediaStack?.data)
                    hideLoader()
                }else{
                    onFailure(call, Throwable(response.message()))
                }
            }

            override fun onFailure(call: Call<MediaStack>, t: Throwable) {
                hideLoader()
                Snackbar.make(
                    findViewById(R.id.main),
                    "Error: ${t.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun loadNews(data: Array<News>?) {
        newsAdapter = NewsAdapter(data!!)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = newsAdapter
    }

    private fun showLoader() {
        homeLoader.visibility = View.VISIBLE
    }
    fun hideLoader() {
        homeLoader.visibility = View.GONE
    }
}