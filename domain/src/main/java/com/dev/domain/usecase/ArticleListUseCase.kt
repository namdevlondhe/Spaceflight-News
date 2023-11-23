package com.dev.domain.usecase

import com.dev.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleListUseCase {
    suspend operator fun invoke() : Flow<List<Article>>

}