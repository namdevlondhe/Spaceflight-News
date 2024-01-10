package com.dev.domain.fakes

import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FakeData {
    fun getArticles(): Flow<ArticleResult> = flow {
        val characters = ArticleResult(
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
        emit(characters)
    }

    fun getArticle(): Flow<Article> = flow {
        val character = Article(
            1,
            "Title",
            "https://url.com",
            "https://utl.com",
            "site",
            "Aummary",
            "10/10/2023",
            false
        )
        emit(character)
    }
}