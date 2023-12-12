package com.dev.domain.usecase

import com.dev.domain.model.Article
import com.dev.domain.repository.ArticleRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetArticleDetailUseCaseTest {

    @Test
    fun `invoke with valid id should return article details`() = runBlocking {
        // Given
        val mockRepository = mockk<ArticleRepository>()
        val getArticleDetailUseCase = GetArticleDetailUseCase(mockRepository)
        val expectedArticleDetail = createMockArticleDetail()

        // When
        coEvery { mockRepository.getArticleDetails(any()) } returns expectedArticleDetail
        val result = getArticleDetailUseCase.invoke(1)

        // Then
        result.collect { article ->
            assertEquals(expectedArticleDetail, article)
        }
    }

    @Test(expected = Exception::class)
    fun `invoke with invalid id should throw an exception`() = runBlocking {
        // Given
        val mockRepository = mockk<ArticleRepository>()
        val getArticleDetailUseCase = GetArticleDetailUseCase(mockRepository)

        // When
        coEvery { mockRepository.getArticleDetails(any()) } throws Exception("Article not found")
        getArticleDetailUseCase.invoke(invalidArticleId)
    }

    private fun createMockArticleDetail(): Flow<Article> {
        return mockk<Flow<Article>>()
    }

    companion object {
        const val invalidArticleId = -1
    }
}
