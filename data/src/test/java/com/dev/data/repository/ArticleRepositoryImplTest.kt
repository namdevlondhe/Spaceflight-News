package com.dev.data.repository

import com.dev.data.mapper.ArticleResultDataMapper
import com.dev.data.source.remote.RetrofitService
import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ArticleRepositoryImplTest {

    @Test
    fun `WHEN getArticles invoked THEN ArticleResult is retrofitService THEN returns data`() =
        runBlocking {
            val mockArticles = mockk<com.dev.data.dto.ArticleResultModel>()
            val articleResul = mockk<ArticleResult>()
            val retrofitService = mockk<RetrofitService>()
            val articleResultDataMapper = mockk<ArticleResultDataMapper>()
            val repository =
                ArticleRepositoryImpl(retrofitService, articleResultDataMapper, Dispatchers.IO)

            coEvery { retrofitService.getArticles() } returns mockArticles
            coEvery { articleResultDataMapper.map(any()) } returns articleResul

            repository.getArticles().toList()

            coVerify(exactly = 1) { retrofitService.getArticles() }
            coVerify(exactly = 1) { articleResultDataMapper.map(any()) }
        }

    @Test
    fun `WHEN getArticleDetails invoked THEN Article is retrofitService THEN returns data`() =
        runBlocking {
            val mockArticle = mockk<Article>()
            val retrofitService = mockk<RetrofitService>()
            val articleResultDataMapper = mockk<ArticleResultDataMapper>()
            val repository =
                ArticleRepositoryImpl(retrofitService, articleResultDataMapper, Dispatchers.IO)

            coEvery { retrofitService.getArticleDetail(any()) } returns mockArticle

            repository.getArticleDetails(1).toList()

            coVerify(exactly = 1) { retrofitService.getArticleDetail(any()) }
        }
}
