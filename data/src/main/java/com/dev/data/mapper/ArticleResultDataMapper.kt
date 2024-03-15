package com.dev.data.mapper

import com.dev.data.dto.ArticleResultModel
import com.dev.domain.model.ArticleResult
import javax.inject.Inject

class ArticleResultDataMapper @Inject constructor() {
    fun map(model: ArticleResultModel) = with(model) {
        ArticleResult(count, next, previous, results)
    }
}