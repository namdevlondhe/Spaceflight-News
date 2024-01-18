package com.dev.data.mapper
import com.dev.data.dto.ArticleModel
import com.dev.data.fakes.FakeCharactersList
import io.mockk.mockk
import io.mockk.every
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleDataMapperTest {

    private val articleDataMapper = ArticleDataMapper()

    @Test
    fun `WHEN map invoked THEN Article is returned`() {
        val mockArticleData = mockk<ArticleModel>()
        val expectedArticle = FakeCharactersList.getArticle()

        every { articleDataMapper.map(mockArticleData) } returns expectedArticle

        val result = articleDataMapper.map(FakeCharactersList.getArticleData())

        assertEquals(expectedArticle, result)
    }
}
