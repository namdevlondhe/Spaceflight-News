package com.dev.domain.usecase

import com.dev.domain.model.Article
import com.dev.domain.repository.ArticleRepository
import com.dev.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetArticleDetailUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) : SingleUseCase<Article>() {
    override fun buildUseCaseSingle(): Single<Article> {
        return articleRepository.getArticleDetails(1)
    }
}