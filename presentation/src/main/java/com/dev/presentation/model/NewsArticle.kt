package com.dev.presentation.model

/**
 * Data class for news article
 */
data class NewsArticle(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite:String,
    val summary:String,
    val publishedAt:String,
    val featured:Boolean
)