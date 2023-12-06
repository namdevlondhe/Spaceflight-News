package com.dev.presentation.mapper

import com.dev.domain.model.Article
import com.dev.presentation.model.NewsArticle
import javax.inject.Inject

/**
 * This is the mapper class to map the data from one model to another
 */
class NewsArticleMapper @Inject constructor() {
    /**
     * This function is responsible to return the NewsArticle from Article
     * @param model - Article model
     */
    fun mapFromModel(model: Article): NewsArticle {
        return with(model) {
            NewsArticle(
                id = id,
                title = title,
                url = url,
                image_url = image_url,
                news_site = news_site,
                summary = summary,
                published_at = published_at,
                featured = featured
            )
        }
    }
}