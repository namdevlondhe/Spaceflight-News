package com.dev.domain.model

data class ArticleResult(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<Article>
)