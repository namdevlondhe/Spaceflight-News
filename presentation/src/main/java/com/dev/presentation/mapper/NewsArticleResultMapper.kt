package com.dev.presentation.mapper

import com.dev.domain.model.ArticleResult
import javax.inject.Inject

/**
 * This is the mapper class to map the data from one model to another
 */
class NewsArticleResultMapper @Inject constructor(
    private val characterMapper: NewsArticleMapper
) {
    /**
     * This function is responsible to return the list of NewsArticle from ArticleResult
     * @param model - ArticleResult model
     */
    fun map(model: ArticleResult) = with(model) {
        results.map { characterMapper.map(it) }
    }
}