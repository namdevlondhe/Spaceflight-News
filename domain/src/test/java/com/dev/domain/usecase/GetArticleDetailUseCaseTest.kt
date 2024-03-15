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

class GetArticleDetailUseCaseTest {
    private val characterRepository = mockk<ArticleRepository>()
    private lateinit var getCharacterByIdUseCaseImpl: GetArticleDetailUseCase

    @Before
    fun setup() {
        MockKAnnotations.init()
        getCharacterByIdUseCaseImpl = GetArticleDetailUseCase(characterRepository)
    }

    @Test
    fun `GIVEN id WHEN use-case invoke called THEN character detail return`() = runTest {
        val character = FakeData.getArticle()
        coEvery { characterRepository.getArticleDetails(ID) } returns Result.success(character)

        getCharacterByIdUseCaseImpl.invoke(ID)

        verify(times(1)) {
            characterRepository.getArticleDetails(ID)
        }
    }

    @Test(expected = IOException::class)
    fun `GIVEN id WHEN use-case invoke called THEN exception thrown`() = runTest {
        coEvery { characterRepository.getArticleDetails(ID) } answers {
            throw IOException()
        }

        getCharacterByIdUseCaseImpl.invoke(ID)

        verify(times(1)) {
            characterRepository.getArticleDetails(ID)
        }
    }

    private companion object {
        const val ID = 36
    }
}