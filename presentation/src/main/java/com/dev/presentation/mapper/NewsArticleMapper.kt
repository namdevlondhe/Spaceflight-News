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
    fun map(model: Article) = with(model) {
        NewsArticle(
            id, title, url, image_url, news_site, summary, published_at, featured
        )
    }
}