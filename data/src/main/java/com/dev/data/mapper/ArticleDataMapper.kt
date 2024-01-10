package com.dev.data.mapper

import com.dev.data.dto.ArticleData
import com.dev.domain.model.Article
import javax.inject.Inject

class ArticleDataMapper @Inject constructor() {
    fun map(model: ArticleData) = with(model) {
        Article(id, title, url, imageUrl, newsSite, summary, publishedAt, featured)
    }
}