package com.dev.domain.usecase

import com.dev.domain.model.Article
import com.dev.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleDetailUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) : ArticleDetailUseCase {
    override suspend fun invoke(id:Int): Flow<Article> {
        return articleRepository.getArticleDetails(id)
    }
}