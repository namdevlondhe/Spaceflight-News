package com.dev.domain.usecase

import com.dev.domain.model.ArticleResult
import com.dev.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleListUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) : ArticleListUseCase {
    override suspend fun invoke(): Flow<ArticleResult> {
        return articleRepository.getArticles()
    }

}