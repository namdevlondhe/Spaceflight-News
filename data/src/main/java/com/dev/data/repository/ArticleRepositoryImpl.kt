package com.dev.data.repository

import com.dev.data.mapper.ArticleResultDataMapper
import com.dev.data.mapper.ArticleDataMapper
import com.dev.data.source.remote.RetrofitService
import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import com.dev.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    private val articleResultDataMapper: ArticleResultDataMapper,
    private val articleDataMapper: ArticleDataMapper
) : ArticleRepository {

    override suspend fun getArticles(): Flow<ArticleResult> = flow {
        val characters = retrofitService.getArticles()
        emit(articleResultDataMapper.mapFromModel(characters))
    }

    override suspend fun getArticleDetails(id: Int): Flow<Article> = flow {
        val character = retrofitService.getArticleDetail(id)
        emit(articleDataMapper.mapFromModel(character))
    }
}