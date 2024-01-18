package com.dev.domain.fakes

import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult

object FakeData {
    fun getArticles(): ArticleResult = ArticleResult(
            1, "Next", "Previous",
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

    fun getArticle(): Article = Article(
            1,
            "Title",
            "https://url.com",
            "https://utl.com",
            "site",
            "Aummary",
            "10/10/2023",
            false
        )
}