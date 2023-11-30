package com.dev.data.dto

import com.dev.domain.model.Article

data class ArticleResultData(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<Article>
)