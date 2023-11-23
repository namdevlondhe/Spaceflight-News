package com.dev.presentation.model

data class NewsArticle(
    val id: Int,
    val title: String,
    val url: String,
    val image_url: String,
    val news_site:String,
    val summary:String,
    val published_at:String,
    val featured:Boolean
)