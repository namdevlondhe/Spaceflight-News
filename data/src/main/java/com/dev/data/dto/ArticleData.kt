package com.dev.data.dto

data class ArticleData(
    val id: Int,
    val title: String,
    val url: String,
    val image_url: String,
    val news_site:String,
    val summary:String,
    val published_at:String,
    val featured:Boolean
)