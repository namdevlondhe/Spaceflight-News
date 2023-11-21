package com.dev.domain.usecase

import com.dev.domain.model.Article
import com.dev.domain.repository.ArticleRepository
import com.dev.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) : SingleUseCase<List<Article>>() {
    override fun buildUseCaseSingle(): Single<List<Article>> {
        return articleRepository.getArticles()
    }
}