package com.dev.data.source.remote

import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("v4/articles/")
    suspend fun getArticles(): Flow<ArticleResult>

    @GET("v4/articles/{id}")
    suspend fun getArticleDetail(@Path("id") id: Int): Flow<Article>
}
