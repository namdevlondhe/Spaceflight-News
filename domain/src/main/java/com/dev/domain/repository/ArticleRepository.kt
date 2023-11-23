package com.dev.domain.repository

import com.dev.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticles(): Flow<List<Article>>
    fun getArticleDetails(id:Int): Flow<Article>
}