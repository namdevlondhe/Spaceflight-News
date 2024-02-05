package com.dev.domain.usecase

import com.dev.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticleDetailUseCase @Inject constructor(private val articleRepository: ArticleRepository) {
    suspend fun invoke(id: Int) = articleRepository.getArticleDetails(id)
}