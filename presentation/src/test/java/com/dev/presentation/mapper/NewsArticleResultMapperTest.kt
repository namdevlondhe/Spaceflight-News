package com.dev.presentation.mapper

import com.dev.domain.fakes.FakeData
import com.dev.domain.model.ArticleResult
import io.mockk.mockk
import io.mockk.every
import junit.framework.TestCase.assertEquals
import org.junit.Test

class NewsArticleResultMapperTest {

    private val newsArticleMapper = mockk<NewsArticleMapper>()
    private val newsArticleResultMapper = NewsArticleResultMapper(newsArticleMapper)

    @Test
    fun `WHEN map invoked THEN List of NewsArticle is returned`() {
        val mockArticleResult = FakeData.getArticles()
        val mockNewsArticle1 = FakeData.getNewsArticle()
        val mockNewsArticle2 = FakeData.getNewsArticle()
        val mockArticle = FakeData.getArticle()

        every { newsArticleMapper.map(mockArticle) } returnsMany listOf(mockNewsArticle1, mockNewsArticle2)
        every { newsArticleResultMapper.map(mockk<ArticleResult>()) } returns listOf(mockNewsArticle1, mockNewsArticle2)

        val result = newsArticleResultMapper.map(mockArticleResult)

        assertEquals(listOf(mockNewsArticle2), result)
    }
}
