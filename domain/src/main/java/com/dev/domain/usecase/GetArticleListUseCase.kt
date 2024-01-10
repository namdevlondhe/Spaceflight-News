package com.dev.domain.usecase

import com.dev.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticleListUseCase @Inject constructor(private val articleRepository: ArticleRepository) :
    ArticleListUseCase {
    override suspend fun invoke() = articleRepository.getArticles()
}