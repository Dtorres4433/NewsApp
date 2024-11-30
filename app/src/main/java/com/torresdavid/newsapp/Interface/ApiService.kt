package com.torresdavid.newsapp.Interface

import com.torresdavid.newsapp.Classes.MediaStack
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("news?access_key=YOUR_API_KEY&countries=us")
    fun getNews(): Call<MediaStack>
    @GET("news?access_key=YOUR_API_KEY&countries=us& categories=business")
    fun getNewsBusiness(): Call<MediaStack>
    @GET("news?access_key=YOUR_API_KEY&countries=us& categories=entertainment")
    fun getNewsEntertainment(): Call<MediaStack>
    @GET("news?access_key=YOUR_API_KEY&countries=us& categories=health")
    fun getNewsHealth(): Call<MediaStack>
    @GET("news?access_key=YOUR_API_KEY&countries=us& categories=science")
    fun getNewsScience(): Call<MediaStack>
    @GET("news?access_key=YOUR_API_KEY&countries=us& categories=sports")
    fun getNewsSports(): Call<MediaStack>
    @GET("news?access_key=YOUR_API_KEY&countries=us& categories=technology")
    fun getNewsTechnology(): Call<MediaStack>
}