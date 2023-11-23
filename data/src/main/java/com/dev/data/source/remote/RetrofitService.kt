package com.dev.data.source.remote

import com.dev.domain.model.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("v4/articles/")
    fun getArticles(): Flow<List<Article>>

    @GET("v4/articles/{id}")
    fun getArticleDetail(@Path("id") id: Int): Flow<Article>
}
