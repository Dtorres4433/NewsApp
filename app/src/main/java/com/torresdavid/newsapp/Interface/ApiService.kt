package com.torresdavid.newsapp.Interface

import com.torresdavid.newsapp.Classes.Pagination
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getNews(): Pagination
}