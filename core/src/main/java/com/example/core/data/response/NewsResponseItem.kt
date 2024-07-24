package com.example.core.data.response


import com.google.gson.annotations.SerializedName

data class NewsResponseItem(
    @SerializedName("banner_url")
    val bannerUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("time_created")
    val timeCreated: Int,
    @SerializedName("title")
    val title: String
)