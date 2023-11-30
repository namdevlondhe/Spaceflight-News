package com.dev.data.mapper

import com.dev.data.dto.ArticleData
import com.dev.data.dto.ArticleResultData
import com.dev.domain.model.ArticleResult
import javax.inject.Inject

class ArticleResultDataMapperclass @Inject constructor(
private val characterMapper: EntityDataMapper
)  {
    fun mapFromModel(model: ArticleResultData):ArticleResult {
        return with(model) {
            ArticleResult(count = count,
            next = next,
            previous = previous,
            results=results)
        }
    }
}