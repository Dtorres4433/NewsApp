package com.torresdavid.newsapp.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.torresdavid.newsapp.Adapter.NewsAdapter
import com.torresdavid.newsapp.Adapter.RetrofitAdapter
import com.torresdavid.newsapp.Classes.Articles
import com.torresdavid.newsapp.Classes.NewsAPI
import com.torresdavid.newsapp.Interface.ApiService
import com.torresdavid.newsapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {
    private lateinit var homeLoader: LottieAnimationView
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var chipGroup: ChipGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_news)
        homeLoader = findViewById(R.id.homeLoader)
        recyclerView = findViewById(R.id.recyclerView)
        chipGroup = findViewById(R.id.chipGroup)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showLoader()
        getNews("general")
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)
            chip?.let {
                val category = chip.text.toString()
                fetchNewsByCategory(category)
            }
        }
    }

    private fun fetchNewsByCategory(category: String) {
        showLoader()
// For example, you can call different API endpoints based on the category
        when (category) {
            getString(R.string.general) -> getNews("general")
            getString(R.string.business) -> getNews("business")
            getString(R.string.health) -> getNews("health")
            getString(R.string.science) -> getNews("science")
            getString(R.string.sports) -> getNews("sports")
            getString(R.string.technology) -> getNews("technology")
        }
    }

    /**
     * Fetches news data from the MediaStack API.
     *
     * This function performs an asynchronous network request to retrieve news articles.
     * It utilizes Retrofit for handling the HTTP communication and enqueue() for asynchronous execution.
     *
     * On success (HTTP 200 OK):
     *   - It extracts the news data from the MediaStack response body.
     *   - It calls the `loadNews()` function to process and display the retrieved news.
     *   - It calls `hideLoader()` to dismiss any loading indicators.
     *
     * On failure (network error or HTTP error other than 200):
     *   - It calls `hideLoader()` to dismiss any loading indicators.
     *   - It displays an error message using a Snackbar, informing the user about the failure.
     *
     * The response is expected to be of type `MediaStack`, which should contain the news articles in its `data` field.
     *
     * @see RetrofitAdapter
     * @see ApiService
     * @see NewsAPI
     * @see loadNews
     * @see hideLoader
     */
    private fun getNews(category: String) {
        val retrofitAdapter = RetrofitAdapter()
        val apiService = retrofitAdapter.getRetrofit().create(ApiService::class.java)
        val call = when (category){
            "general" -> apiService.getNews()
            "business" -> apiService.getNewsBusiness()
            "entertainment" -> apiService.getNewsEntertainment()
            "health" -> apiService.getNewsHealth()
            "science" -> apiService.getNewsScience()
            "sports" -> apiService.getNewsSports()
            "technology" -> apiService.getNewsTechnology()
            else -> apiService.getNews()
        }
        call.enqueue(object : Callback<NewsAPI> {
            override fun onResponse(call: Call<NewsAPI>, response: Response<NewsAPI>) {
                if (response.code() == 200) {
                    val mediaStack = response.body()
                    loadNews(mediaStack?.articles)
                    hideLoader()
                }else{
                    onFailure(call, Throwable(response.message()))
                }
            }

            override fun onFailure(call: Call<NewsAPI>, t: Throwable) {
                hideLoader()
                Snackbar.make(
                    findViewById(R.id.main),
                    "Error: ${t.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun loadNews(data: Array<Articles>?) {
        newsAdapter = NewsAdapter(data!!)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.adapter = newsAdapter
        newsAdapter.onItemClick = {
            val intent = Intent(this, ContentActivity::class.java)
            intent.putExtra("article", it)
            startActivity(intent)
        }
    }

    private fun showLoader() {
        recyclerView.visibility = View.GONE
        homeLoader.visibility = View.VISIBLE
    }
    fun hideLoader() {
        homeLoader.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}