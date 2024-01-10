package com.dev.data.mapper

import com.dev.data.dto.ArticleResultData
import com.dev.domain.model.ArticleResult
import javax.inject.Inject

class ArticleResultDataMapper @Inject constructor(
    private val characterMapper: ArticleDataMapper
) {
    fun map(model: ArticleResultData) = with(model) {
        ArticleResult(count, next, previous, results)
    }
}