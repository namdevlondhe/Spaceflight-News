package com.dev.domain.usecase

import com.dev.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleDetailUseCase {
    suspend operator fun invoke(id:Int) : Flow<Article>

}