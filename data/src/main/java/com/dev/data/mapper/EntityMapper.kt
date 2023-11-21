package com.dev.data.mapper

import com.dev.data.entity.ArticleData
import com.dev.domain.model.Article

fun Article.toEntity() = ArticleData(
    id = id,
    title = title,
    url = url,
    image_url = image_url,
    news_site = news_site,
    summary = summary,
    published_at = published_at,
    featured = featured
)
