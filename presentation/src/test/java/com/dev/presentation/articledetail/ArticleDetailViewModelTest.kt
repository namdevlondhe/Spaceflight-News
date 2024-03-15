package com.dev.presentation.articledetail

import app.cash.turbine.test
import com.dev.domain.fakes.FakeData
import com.dev.domain.usecase.GetArticleDetailUseCase
import com.dev.presentation.mapper.NewsArticleMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticleDetailViewModelTest {

    private lateinit var viewModel: ArticleDetailViewModel
    private val getArticleDetailUseCase: GetArticleDetailUseCase = mockk()
    private val articleMapper: NewsArticleMapper = mockk()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = ArticleDetailViewModel(getArticleDetailUseCase, articleMapper)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchArticleInfo should emit Loading, Success states when use case returns data`() =
        runTest {
            val data = FakeData.getNewsArticle()
            coEvery { getArticleDetailUseCase(ID) } returns Result.success(FakeData.getArticle())

            coEvery {
                articleMapper.map(FakeData.getArticles().results[0])
            } returns data

            with(viewModel) {
                sendIntent(ArticleDetailViewIntent.LoadData(ID))
                stateFlow.test {
                    advanceUntilIdle()
                    Assert.assertTrue(awaitItem() is ArticleDetailViewState.Success)
                }
            }
        }

    @Test
    fun `fetchArticleInfo should emit Loading, Error states when use case throws an exception`() =
        runTest {
            coEvery { getArticleDetailUseCase(ID) } answers {
                Result.failure(Exception())
            }

            with(viewModel) {
                sendIntent(ArticleDetailViewIntent.LoadData(ID))
                stateFlow.test {

                    Assert.assertTrue(awaitItem() is ArticleDetailViewState.Error)
                }
            }
        }

    companion object {
        const val ID = 1
    }
}

