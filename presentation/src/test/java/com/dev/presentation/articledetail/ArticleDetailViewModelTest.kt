package com.dev.presentation.articledetail

import com.dev.domain.fakes.FakeData
import com.dev.domain.model.Article
import com.dev.domain.usecase.GetArticleDetailUseCase
import com.dev.presentation.mapper.NewsArticleMapper
import com.dev.presentation.model.NewsArticle
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals

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
        runBlocking {
            // Arrange
            val articleId = 1
            val mockNewsArticle = mockk<NewsArticle>()
            val mockArticle = mockk<Article>()
            val mockViewState = ArticleDetailViewState.Success(mockNewsArticle)
            coEvery { getArticleDetailUseCase(articleId) } returns flowOf(mockArticle)
            every { articleMapper.mapFromModel(mockArticle) } returns mockNewsArticle

            // Act
            val stateFlow = MutableStateFlow<ArticleDetailViewState>(ArticleDetailViewState.Loading)
            launch {
                viewModel.stateSharedFlow.collect {
                    stateFlow.value = it
                }
            }
            viewModel.sendIntent(ArticleDetailViewIntent.LoadData(articleId))

            // Assert
            assert(stateFlow.value is ArticleDetailViewState.Success)
            assertEquals(stateFlow.value, mockViewState)
        }

    @Test
    fun `fetchArticleInfo should emit Loading, Error states when use case throws an exception`() =
        runBlocking {
            // Arrange
            val articleId = 1
            val exception = RuntimeException("Test Exception")
            val mockErrorState = ArticleDetailViewState.Error(exception)
            coEvery { getArticleDetailUseCase(articleId) } throws exception

            // Act
            val stateFlow = MutableStateFlow<ArticleDetailViewState>(ArticleDetailViewState.Loading)
            launch {
                viewModel.stateSharedFlow.collect {
                    stateFlow.value = it
                }
            }
            viewModel.sendIntent(ArticleDetailViewIntent.LoadData(articleId))

            // Assert
            assert(stateFlow.value is ArticleDetailViewState.Error)
            assertEquals(stateFlow.value, mockErrorState)
        }
}

