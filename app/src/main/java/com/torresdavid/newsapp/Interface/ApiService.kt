package com.torresdavid.newsapp.Interface

import com.torresdavid.newsapp.BuildConfig.API_KEY
import com.torresdavid.newsapp.Classes.MediaStack
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("news")
    fun getNews(@Query("access_key") accessKey: String = API_KEY, @Query("countries") countries: String = "us"): Call<MediaStack>
    @GET("news")
    fun getNewsBusiness(@Query("access_key") accessKey: String = API_KEY, @Query("countries") countries: String = "us",@Query("categories") category:String = "business"): Call<MediaStack>
    @GET("news")
    fun getNewsEntertainment(@Query("access_key") accessKey: String = API_KEY, @Query("countries") countries: String = "us",@Query("categories") category:String = "entertainment"): Call<MediaStack>
    @GET("news")
    fun getNewsHealth(@Query("access_key") accessKey: String = API_KEY, @Query("countries") countries: String = "us",@Query("categories") category:String = "health"): Call<MediaStack>
    @GET("news")
    fun getNewsScience(@Query("access_key") accessKey: String = API_KEY, @Query("countries") countries: String = "us",@Query("categories") category:String = "science"): Call<MediaStack>
    @GET("news")
    fun getNewsSports(@Query("access_key") accessKey: String = API_KEY, @Query("countries") countries: String = "us",@Query("categories") category:String = "sports"): Call<MediaStack>
    @GET("news")
    fun getNewsTechnology(@Query("access_key") accessKey: String = API_KEY, @Query("countries") countries: String = "us",@Query("categories") category:String = "technology"): Call<MediaStack>
}