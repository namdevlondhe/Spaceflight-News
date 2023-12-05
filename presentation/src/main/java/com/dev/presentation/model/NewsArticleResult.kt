package com.dev.presentation.model

data class NewsArticleResult (
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<NewsArticle>
)