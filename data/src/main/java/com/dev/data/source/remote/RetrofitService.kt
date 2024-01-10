package com.dev.data.source.remote

import com.dev.data.dto.ArticleResultData
import com.dev.domain.model.Article
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("v4/articles/")
    suspend fun getArticles(): ArticleResultData

    @GET("v4/articles/{id}")
    suspend fun getArticleDetail(@Path("id") id: Int): Article
}
