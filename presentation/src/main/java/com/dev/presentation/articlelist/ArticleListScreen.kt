package com.dev.presentation.articlelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev.presentation.R
import com.dev.presentation.ui.theme.BackgroundColor
import com.dev.presentation.ui.theme.UiSize
import com.dev.presentation.ui.theme.UiSize.Companion.SP_SIZE_18
import kotlinx.coroutines.flow.collectLatest

/**
 * This function is responsible to provide the UI for news article list using compose
 */
@Composable
fun ArticleListScreen(
    onListItemClicked: (id: Int) -> Unit
) {
    Surface(
        color = BackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(start = UiSize.SIZE_10, end = UiSize.SIZE_10)) {
            ArticleList(onListItemClicked)
        }
    }
}

@Composable
private fun ArticleList(
    onListItemClicked: (id: Int) -> Unit,
    viewModel: ArticleListViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.sendIntent(ArticleListViewIntent.LoadData)
        viewModel.sideEffectSharedFlow.collectLatest {
            if (it is ArticleListSideEffect.NavigateToDetails) {
                onListItemClicked(it.id)
            }
        }
    }

    val viewState =
        viewModel.stateFlow.collectAsState(initial = ArticleListViewState.Loading)

    when (viewState.value) {
        is ArticleListViewState.Loading -> {
            Box(
                contentAlignment = Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        is ArticleListViewState.Success -> {
            val articleList = (viewState.value as ArticleListViewState.Success).data
            LazyColumn {
                items(articleList.size) {
                    ArticleRow(rowIndex = it, entries = articleList)
                }
            }
        }

        is ArticleListViewState.Error -> {
            (viewState.value as ArticleListViewState.Error).let {
                Box(
                    contentAlignment = Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    RetrySection(it.message) {
                        viewModel.fetchArticleList()
                    }
                }
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = SP_SIZE_18)
        Spacer(modifier = Modifier.height(UiSize.SIZE_8))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.button_retry))
        }
    }
}