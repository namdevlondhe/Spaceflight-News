package com.dev.data.mapper

import com.dev.data.dto.ArticleData
import com.dev.data.dto.ArticleResultData
import com.dev.data.fakes.FakeCharactersList
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleResultDataMapperTest {

    private val characterMapper = mockk<ArticleDataMapper>()
    private val articleResultDataMapper = ArticleResultDataMapper(characterMapper)

    @Test
    fun `WHEN map invoked THEN ArticleResult is returned`() {
        // Given
        val mockArticleResultData = mockk<ArticleResultData>()
        val mockArticleResult = mockk<ArticleData>()
        val expectedArticleResult = FakeCharactersList.getArticleResult()

        every { characterMapper.map(mockArticleResult) } returns FakeCharactersList.getArticle()
        every { articleResultDataMapper.map(mockArticleResultData) } returns FakeCharactersList.getArticleResult()

        // When
        val result = articleResultDataMapper.map(FakeCharactersList.getArticleList())

        // Then
        assertEquals(expectedArticleResult, result)
    }
}
