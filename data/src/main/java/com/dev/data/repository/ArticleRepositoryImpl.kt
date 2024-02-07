package com.dev.data.repository

import com.dev.data.mapper.ArticleResultDataMapper
import com.dev.data.source.remote.RetrofitService
import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import com.dev.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    private val articleResultDataMapper: ArticleResultDataMapper,
) : ArticleRepository {

    override suspend fun getArticles(): Result<ArticleResult>  {
        return try {
            val characters = retrofitService.getArticles()
            Result.success(articleResultDataMapper.map(characters))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getArticleDetails(id: Int): Result<Article> {
        return try {
            Result.success(retrofitService.getArticleDetail(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}