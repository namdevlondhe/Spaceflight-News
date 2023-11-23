package com.dev.data.repository

import com.dev.data.source.remote.RetrofitService
import com.dev.domain.model.Article
import com.dev.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class ArticleRepositoryImpl(
    private val retrofitService: RetrofitService
) : ArticleRepository {
    override fun getArticles(): Flow<List<Article>> {
        return retrofitService.getArticles()
    }

    override fun getArticleDetails(id: Int): Flow<Article> {
        return retrofitService.getArticleDetail(id)
    }
}