package com.dev.presentation.model

import com.dev.domain.model.Article

data class NewsArticleResult (
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<NewsArticle>
)