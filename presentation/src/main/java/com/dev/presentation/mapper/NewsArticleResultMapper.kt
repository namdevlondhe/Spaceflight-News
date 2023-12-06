package com.dev.presentation.mapper

import com.dev.domain.model.ArticleResult
import com.dev.presentation.model.NewsArticle
import javax.inject.Inject

/**
 * This is the mapper class to map the data from one model to another
 */
class NewsArticleResultMapper @Inject constructor(
    private val characterMapper: NewsArticleMapper
)  {
    /**
     * This function is responsible to return the list of NewsArticle from ArticleResult
     * @param model - ArticleResult model
     */
    fun mapFromModel(model: ArticleResult): List<NewsArticle> {
        return with(model) {
            results.map { characterMapper.mapFromModel(it) }
        }
    }
}