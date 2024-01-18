package com.dev.domain.repository

import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getArticles(): Flow<Result<ArticleResult>>
    suspend fun getArticleDetails(id:Int): Flow<Result<Article>>
}