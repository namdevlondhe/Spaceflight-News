package com.dev.domain.usecase

import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import com.dev.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticleListUseCase @Inject constructor(private val articleRepository: ArticleRepository) {
    suspend operator fun invoke(): Result<ArticleResult> = articleRepository.getArticles()
}