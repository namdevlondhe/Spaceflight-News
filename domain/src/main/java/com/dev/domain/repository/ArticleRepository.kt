package com.dev.domain.repository

import com.dev.domain.model.Article
import io.reactivex.Single

interface ArticleRepository {
    fun getArticles(): Single<List<Article>>
    fun getArticleDetails(id:Int): Single<Article>
}