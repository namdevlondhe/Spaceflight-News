package com.dev.data.repository

import com.dev.data.mapper.ArticleResultDataMapper
import com.dev.data.source.remote.RetrofitService
import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import com.dev.domain.repository.ArticleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    private val articleResultDataMapper: ArticleResultDataMapper,
    private val dispatcher: CoroutineDispatcher
) : ArticleRepository {

    override suspend fun getArticles(): Flow<Result<ArticleResult>> = flow {
        try {
            val characters = retrofitService.getArticles()
            emit(Result.success(articleResultDataMapper.map(characters)))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(dispatcher)

    override suspend fun getArticleDetails(id: Int): Flow<Result<Article>> = flow {
        try {
            emit(Result.success(retrofitService.getArticleDetail(id)))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(dispatcher)
}