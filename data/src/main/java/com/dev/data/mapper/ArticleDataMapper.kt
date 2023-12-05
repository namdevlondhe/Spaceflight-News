package com.dev.data.mapper

import com.dev.data.dto.ArticleData
import com.dev.domain.model.Article
import javax.inject.Inject

class ArticleDataMapper @Inject constructor() {
    fun mapFromModel(model: ArticleData): Article {
        return with(model) {
            Article(
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