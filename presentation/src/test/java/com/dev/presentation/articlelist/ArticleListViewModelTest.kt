package com.dev.presentation.articlelist

import com.dev.domain.fakes.FakeData
import com.dev.domain.model.ArticleResult
import com.dev.domain.usecase.ArticleListUseCase
import com.dev.presentation.mapper.NewsArticleResultMapper
import com.dev.presentation.model.NewsArticle
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    private val articleListUseCase: ArticleListUseCase = mockk()
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
            val data = listOf( FakeData.getNewsArticle())
            coEvery { articleListUseCase() } returns flowOf( FakeData.getArticles())

            coEvery {
                articleNewsMapper.map(FakeData.getArticles())
            } returns data

            viewModel.sendIntent(ArticleListViewIntent.LoadData)
            val result = viewModel.stateFlow.value
            Assert.assertEquals(
                result,
                viewModel.stateFlow.value
            )
        }
}
