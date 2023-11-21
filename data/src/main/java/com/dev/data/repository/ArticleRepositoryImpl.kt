package com.dev.data.repository

import com.dev.data.source.remote.RetrofitService
import com.dev.domain.model.Article
import com.dev.domain.repository.ArticleRepository
import io.reactivex.Single

class ArticleRepositoryImpl(
    private val retrofitService: RetrofitService
) : ArticleRepository {
    override fun getArticles(): Single<List<Article>> {
        return retrofitService.getArticles()
    }

    override fun getArticleDetails(id: Int): Single<Article> {
        return retrofitService.getArticleDetail(id)
    }
}