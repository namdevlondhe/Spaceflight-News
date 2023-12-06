package com.dev.presentation.model

/**
 * Data class for news article result
 */
data class NewsArticleResult (
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<NewsArticle>
)