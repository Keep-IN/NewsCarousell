package com.example.core.di

import com.example.core.data.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiContract {
    @GET("/carousell-interview-assets/android/carousell_news.json")
    suspend fun getNews(): Response<NewsResponse>
}