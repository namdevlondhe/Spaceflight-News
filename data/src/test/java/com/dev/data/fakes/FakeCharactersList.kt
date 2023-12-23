package com.mayank.data.fakes

import com.dev.data.dto.ArticleData
import com.dev.data.dto.ArticleResultData
import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult

object FakeCharactersList {
        fun getArticleList() = ArticleResultData(
            count = 1,
            next = "Next",
            previous = "Previous",
            results = listOf<Article>(
                Article(
                    id = 1,
                    title = "Title",
                    url = "https://url.com",
                    image_url  = "https://utl.com",
                    news_site = "site",
                    summary = "Aummary",
                    published_at = "10/10/2023",
                    featured = false
                )
            )
        )


        fun getArticle() = Article(
            id = 1,
            title = "Title",
            url = "https://url.com",
            image_url  = "https://utl.com",
            news_site = "site",
            summary = "Aummary",
            published_at = "10/10/2023",
            featured = false
        )

        fun getArticleResult() = ArticleResult(
            count = 1,
            next = "Next",
            previous = "Previous",
            results = listOf<Article>(
                Article(
                    id = 1,
                    title = "Title",
                    url = "https://url.com",
                    image_url  = "https://utl.com",
                    news_site = "site",
                    summary = "Aummary",
                    published_at = "10/10/2023",
                    featured = false
                )
            )
        )

        fun getArticleData() = ArticleData(
            id = 1,
            title = "Title",
            url = "https://url.com",
            image_url  = "https://utl.com",
            news_site = "site",
            summary = "Aummary",
            published_at = "10/10/2023",
            featured = false
        )

}