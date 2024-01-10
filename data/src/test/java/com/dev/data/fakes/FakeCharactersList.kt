package com.dev.data.fakes

import com.dev.data.dto.ArticleData
import com.dev.data.dto.ArticleResultData
import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult

object FakeCharactersList {
    fun getArticleList() = ArticleResultData(
        1,
        "Next",
        "Previous",
        listOf<Article>(
            Article(
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
    )

    fun getArticle() = Article(
        1,
        "Title",
        "https://url.com",
        "https://utl.com",
        "site",
        "Aummary",
        "10/10/2023",
        false
    )

    fun getArticleResult() = ArticleResult(
        1,
        "Next",
        "Previous",
        listOf<Article>(
            Article(
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
    )

    fun getArticleData() = ArticleData(
        id = 1,
        title = "Title",
        url = "https://url.com",
        imageUrl = "https://utl.com",
        newsSite = "site",
        summary = "Aummary",
        publishedAt = "10/10/2023",
        featured = false
    )
}