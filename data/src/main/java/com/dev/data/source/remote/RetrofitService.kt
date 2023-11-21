package com.dev.data.source.remote

import com.dev.domain.model.Article
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("v4/articles/")
    fun getArticles(): Single<List<Article>>

    @GET("v4/articles/{id}")
    fun getArticleDetail(@Path("id") id: Int): Single<Article>
}
