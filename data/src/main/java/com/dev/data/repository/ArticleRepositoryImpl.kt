package com.dev.data.repository

import com.dev.data.mapper.ArticleResultDataMapperclass
import com.dev.data.mapper.EntityDataMapper
import com.dev.data.source.remote.RetrofitService
import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import com.dev.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ArticleRepositoryImpl(
    private val retrofitService: RetrofitService,
    private val entityDataMapper: ArticleResultDataMapperclass
) : ArticleRepository {
    override suspend fun getArticles(): Flow<ArticleResult>  {
        return retrofitService.getArticles()
    }
    override suspend fun getArticleDetails(id: Int): Flow<Article> {
        return retrofitService.getArticleDetail(id)
    }
}