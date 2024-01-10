package com.dev.data.mapper
import com.dev.data.dto.ArticleData
import com.dev.data.fakes.FakeCharactersList
import io.mockk.mockk
import io.mockk.every
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleDataMapperTest {

    private val articleDataMapper = ArticleDataMapper()

    @Test
    fun `WHEN map invoked THEN Article is returned`() {
        // Given
        val mockArticleData = mockk<ArticleData>()
        val expectedArticle = FakeCharactersList.getArticle()

        every { articleDataMapper.map(mockArticleData) } returns expectedArticle

        // When
        val result = articleDataMapper.map(FakeCharactersList.getArticleData())

        // Then
        assertEquals(expectedArticle, result)
    }
}
