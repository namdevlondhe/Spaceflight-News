package com.dev.domain.fakes

import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FakeData {
    fun getArticles(): Flow<ArticleResult> = flow {
        val characters = ArticleResult(count = 1, next = "Next", previous = "Previous", results =
        listOf<Article>(
            Article(id = 1,
                title = "Title",
                url = "https://url.com",
                image_url  = "https://utl.com",
                news_site = "site",
                summary = "Aummary",
                published_at = "10/10/2023",
                featured = false)
        )
        )
        emit(characters)
    }

    fun getArticle(): Flow<Article> = flow {
        val character = Article(
            id = 1,
            title = "Title",
            url = "https://url.com",
            image_url  = "https://utl.com",
            news_site = "site",
            summary = "Aummary",
            published_at = "10/10/2023",
            featured = false
        )
        emit(character)
    }
}