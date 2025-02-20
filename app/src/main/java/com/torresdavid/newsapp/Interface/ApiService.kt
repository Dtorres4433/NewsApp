package com.torresdavid.newsapp.Interface

import com.torresdavid.newsapp.BuildConfig.API_KEY
import com.torresdavid.newsapp.Classes.MediaStack
import com.torresdavid.newsapp.Classes.NewsAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getNews(@Query("apiKey") accessKey: String = API_KEY, @Query("country") countries: String = "us"): Call<NewsAPI>
    @GET("top-headlines")
    fun getNewsBusiness(@Query("apiKey") accessKey: String = API_KEY, @Query("country") countries: String = "us",@Query("category") category:String = "business"): Call<NewsAPI>
    @GET("top-headlines")
    fun getNewsEntertainment(@Query("apiKey") accessKey: String = API_KEY, @Query("country") countries: String = "us",@Query("category") category:String = "entertainment"): Call<NewsAPI>
    @GET("top-headlines")
    fun getNewsHealth(@Query("apiKey") accessKey: String = API_KEY, @Query("country") countries: String = "us",@Query("category") category:String = "health"): Call<NewsAPI>
    @GET("top-headlines")
    fun getNewsScience(@Query("apiKey") accessKey: String = API_KEY, @Query("country") countries: String = "us",@Query("category") category:String = "science"): Call<NewsAPI>
    @GET("top-headlines")
    fun getNewsSports(@Query("apiKey") accessKey: String = API_KEY, @Query("country") countries: String = "us",@Query("category") category:String = "sports"): Call<NewsAPI>
    @GET("top-headlines")
    fun getNewsTechnology(@Query("apiKey") accessKey: String = API_KEY, @Query("country") countries: String = "us",@Query("category") category:String = "technology"): Call<NewsAPI>
}