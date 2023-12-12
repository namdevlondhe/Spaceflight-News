package com.dev.domain.usecase

import com.dev.domain.model.Article
import com.dev.domain.model.ArticleResult
import com.dev.domain.repository.ArticleRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetArticleListUseCaseTest {

    @Test
    fun `invoke should return article list`() = runBlocking {
        // Given
        val mockRepository = mockk<ArticleRepository>()
        val getArticleListUseCase = GetArticleListUseCase(mockRepository)
        val expectedArticleList = createMockArticleResult()

        // When
        coEvery { mockRepository.getArticles() } returns expectedArticleList
        val result = getArticleListUseCase.invoke()

        // Then
        result.collect { articleResult ->
            assertEquals(expectedArticleList, articleResult)
        }
    }

    @Test(expected = Exception::class)
    fun `invoke with repository exception should throw an exception`() = runBlocking {
        // Given
        val mockRepository = mockk<ArticleRepository>()
        val getArticleListUseCase = GetArticleListUseCase(mockRepository)

        // When
        coEvery { mockRepository.getArticles() } throws Exception("Error fetching articles")
        getArticleListUseCase.invoke()
    }

    private fun createMockArticleResult(): Flow<ArticleResult> {
        return mockk<Flow<ArticleResult>>()
    }

    private fun createMockArticle(): Article {
        return mockk<Article>()
    }
}
