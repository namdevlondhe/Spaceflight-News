package com.dev.domain.usecase

import com.dev.domain.model.ArticleResult
import kotlinx.coroutines.flow.Flow

interface ArticleListUseCase {
    suspend operator fun invoke() : Flow<ArticleResult>
}