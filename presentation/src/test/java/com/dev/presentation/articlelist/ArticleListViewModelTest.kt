package com.dev.presentation.articlelist

import com.dev.domain.model.ArticleResult
import com.dev.domain.usecase.ArticleListUseCase
import com.dev.presentation.mapper.NewsArticleResultMapper
import com.dev.presentation.model.NewsArticle
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
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
    fun `fetchArticleList should emit Loading, Success states when use case returns data`() =
        runBlocking {
            // Arrange
            val mockArticleResult = mockk<ArticleResult>()
            val mockArticleList = mockk<List<NewsArticle>>()
            every { articleNewsMapper.mapFromModel(mockArticleResult) } returns mockArticleList
            val mockViewState = ArticleListViewState.Success(articleNewsMapper.mapFromModel(mockArticleResult))
            coEvery { articleListUseCase.invoke() } returns flowOf(mockArticleResult)


            // Act
            val stateFlow = MutableStateFlow<ArticleListViewState>(mockViewState)
            launch {
                viewModel.stateSharedFlow.collect()
            }
            viewModel.fetchArticleList()

            // Assert

            assert(stateFlow.value is ArticleListViewState.Success)
            assertEquals(stateFlow.value , mockViewState)
        }


}
