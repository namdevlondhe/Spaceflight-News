package com.dev.presentation.mapper

import com.dev.domain.model.Article
import com.dev.presentation.model.NewsArticle
import javax.inject.Inject

class NewsArticleMapper @Inject constructor() {
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