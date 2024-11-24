package com.torresdavid.newsapp.Adapter

import retrofit2.Retrofit

class RetrofitAdapter {
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://api.mediastack.com/v1/news")
            .build()
    }
}