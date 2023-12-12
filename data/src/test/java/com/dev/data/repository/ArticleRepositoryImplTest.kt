package com.dev.data.repository

import com.dev.data.dto.ArticleData
import com.dev.data.dto.ArticleResultData
import com.dev.data.mapper.ArticleDataMapper
import com.dev.data.mapper.ArticleResultDataMapper
import com.dev.data.source.remote.RetrofitService
import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ArticleRepositoryImplTest {

    @Test
    fun `getArticles should return ArticleResult when retrofitService returns data`() = runBlockingTest {
        // Given
        val mockArticles = mockk<ArticleResultData>()
        val articleResul  = mockk<ArticleResult>()
        val retrofitService = mockk<RetrofitService>()
        val articleResultDataMapper = mockk<ArticleResultDataMapper>()
        val articleDataMapper = mockk<ArticleDataMapper>()
        val repository = ArticleRepositoryImpl(retrofitService, articleResultDataMapper, articleDataMapper)

        coEvery { retrofitService.getArticles() } returns mockArticles
        coEvery { articleResultDataMapper.mapFromModel(any()) } returns articleResul

        // When
        repository.getArticles().toList()

        // Then
        // Verify that there are no more interactions with the mocks
        coVerify(exactly = 1) { retrofitService.getArticles() }
        coVerify(exactly = 1) { articleResultDataMapper.mapFromModel(any()) }
    }

    @Test
    fun `getArticleDetails should return Article when retrofitService returns data`() = runBlockingTest {
        // Given
        val mockArticle = mockk<ArticleData>()
        val articleResul  = mockk<Article>()
        val retrofitService = mockk<RetrofitService>()
        val articleResultDataMapper = mockk<ArticleResultDataMapper>()
        val articleDataMapper = mockk<ArticleDataMapper>()
        val repository = ArticleRepositoryImpl(retrofitService, articleResultDataMapper, articleDataMapper)

        coEvery { retrofitService.getArticleDetail(any()) } returns mockArticle
        coEvery { articleDataMapper.mapFromModel(any()) } returns articleResul

        // When
        repository.getArticleDetails(1).toList()

        // Then
        // Verify that there are no more interactions with the mocks
        coVerify(exactly = 1) { retrofitService.getArticleDetail(any()) }
        coVerify(exactly = 1) { articleDataMapper.mapFromModel(any()) }
    }
}
