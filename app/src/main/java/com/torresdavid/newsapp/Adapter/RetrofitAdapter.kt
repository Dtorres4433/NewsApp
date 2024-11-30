package com.torresdavid.newsapp.Adapter

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitAdapter {
    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.mediastack.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}