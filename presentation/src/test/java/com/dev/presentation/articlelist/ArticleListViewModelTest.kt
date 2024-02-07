package com.dev.presentation.articlelist

import app.cash.turbine.test
import com.dev.domain.fakes.FakeData
import com.dev.domain.usecase.GetArticleListUseCase
import com.dev.presentation.mapper.NewsArticleResultMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticleListViewModelTest {

    private lateinit var viewModel: ArticleListViewModel
    private val articleListUseCase: GetArticleListUseCase = mockk()
    private val articleNewsMapper: NewsArticleResultMapper = mockk()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = ArticleListViewModel(articleListUseCase, articleNewsMapper)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `Given fetchArticleList is called, When use case returns data, Then Loading and Success states should be emitted`() =
        runTest {
            val data = FakeData.getNewsArticleList()
            coEvery { articleListUseCase.invoke() } returns Result.success(FakeData.getArticles())

            coEvery {
                articleNewsMapper.map(FakeData.getArticles())
            } returns data

            viewModel.sendIntent(ArticleListViewIntent.LoadData)

            viewModel.stateFlow.test {
                advanceUntilIdle()
                Assert.assertTrue((awaitItem() is ArticleListViewState.Success))
            }
        }

    @Test
    fun `fetch article list failed GIVEN intent WHEN fetchArticleList called THEN verify use-case called to get success result`() =
        runTest {
            coEvery { articleListUseCase.invoke() } answers {
                Result.failure(Exception())
            }

            viewModel.sendIntent(ArticleListViewIntent.LoadData)

            viewModel.stateFlow.test {
                Assert.assertTrue(awaitItem() is ArticleListViewState.Error)
            }
        }
}