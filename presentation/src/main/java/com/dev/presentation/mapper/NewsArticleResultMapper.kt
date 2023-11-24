package com.dev.presentation.mapper

import com.dev.domain.model.ArticleResult
import com.dev.presentation.model.NewsArticle
import com.dev.presentation.model.NewsArticleResult
import javax.inject.Inject

class NewsArticleResultMapper @Inject constructor(
    private val characterMapper: ArticleNewsMapper
)  {
    fun mapFromModel(model: ArticleResult): List<NewsArticle> {
        return with(model) {
            results.map { characterMapper.mapFromModel(it) }
        }
    }
}