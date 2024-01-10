package com.dev.data.dto

data class ArticleData(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite:String,
    val summary:String,
    val publishedAt:String,
    val featured:Boolean
)