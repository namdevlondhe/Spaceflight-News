package com.dev.presentation.mapper
import com.dev.domain.fakes.FakeData
import com.dev.domain.model.Article
import io.mockk.mockk
import io.mockk.every
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsArticleMapperTest {

    private val newsArticleMapper = NewsArticleMapper()

    @Test
    fun `WHEN map invoked THEN NewsArticle is returned`() {
        // Given
        val mockArticle = mockk<Article>()
        val expectedNewsArticle = FakeData.getNewsArticle()

        every { newsArticleMapper.map(mockArticle) } returns FakeData.getNewsArticle()

        // When
        val result = newsArticleMapper.map(FakeData.getArticle())

        // Then
        assertEquals(expectedNewsArticle, result)
    }
}
