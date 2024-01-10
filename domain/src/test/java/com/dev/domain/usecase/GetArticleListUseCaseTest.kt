package com.dev.domain.usecase

import com.dev.domain.fakes.FakeData
import com.dev.domain.repository.ArticleRepository
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetArticleListUseCaseTest {
    private val characterRepository = mockk<ArticleRepository>()
    private lateinit var getCharactersUseCaseImpl: GetArticleListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getCharactersUseCaseImpl = GetArticleListUseCase(characterRepository)
    }

    @Test
    fun `GIVEN no data WHEN use-case invoke called THEN character list return`() = runTest {
        val characters = FakeData.getArticles()
        coEvery { characterRepository.getArticles() } returns characters

        getCharactersUseCaseImpl()

        verify(times(1)) {
            characterRepository.getArticles()
        }
    }

    @Test(expected = IOException::class)
    fun `GIVEN no data WHEN use-case invoke called THEN exception thrown`() = runTest {
        coEvery { characterRepository.getArticles() } answers {
            throw IOException()
        }

        getCharactersUseCaseImpl()

        verify(times(1)) {
            characterRepository.getArticles()
        }
    }
}