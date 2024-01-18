package com.dev.domain.fakes

import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import com.dev.presentation.model.NewsArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FakeData {
    fun getArticles(): ArticleResult = ArticleResult(
        count = 1, next = "Next", previous = "Previous", results =
        listOf<Article>(
            Article(
                id = 1,
                title = "Title",
                url = "https://url.com",
                image_url = "https://utl.com",
                news_site = "site",
                summary = "Aummary",
                published_at = "10/10/2023",
                featured = false
            )
        )
    )

    fun getArticle(): Article = Article(
        id = 1,
        title = "Title",
        url = "https://url.com",
        image_url = "https://utl.com",
        news_site = "site",
        summary = "Aummary",
        published_at = "10/10/2023",
        featured = false
    )

    fun getNewsArticle(): NewsArticle = NewsArticle(
        1,
        "Title",
        "https://url.com",
        "https://utl.com",
        "site",
        "Aummary",
        "10/10/2023",
        false
    )

    fun getNewsArticleList(): List<NewsArticle> = arrayListOf(
        NewsArticle(
            1,
            "Title",
            "https://url.com",
            "https://utl.com",
            "site",
            "Aummary",
            "10/10/2023",
            false
        )
    )
}