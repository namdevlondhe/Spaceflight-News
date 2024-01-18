package com.dev.presentation.articlelist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dev.presentation.R
import com.dev.presentation.model.NewsArticle
import com.dev.presentation.ui.common.HeaderTitle
import com.dev.presentation.ui.theme.BackgroundColor
import com.dev.presentation.ui.theme.SubTextColor
import com.dev.presentation.ui.theme.TextColor
import com.dev.presentation.ui.theme.UiSize
import com.dev.presentation.ui.theme.UiSize.Companion.SP_SIZE_16
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
        Column(modifier = Modifier.padding(start = UiSize.SIZE_28, end = UiSize.SIZE_25)) {
            ArticleScreenHeader()

            Spacer(modifier = Modifier.height(UiSize.SIZE_16))

            ArticleList(onListItemClicked)
        }
    }
}

@Composable
private fun ArticleScreenHeader() {
    Divider(
        modifier = Modifier
            .width(UiSize.SIZE_318)
            .height(UiSize.SIZE_1)
            .background(color = SubTextColor)
    )
    HeaderTitle(title = stringResource(R.string.sub_title))
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
            (viewState.value as ArticleListViewState.Error).throwable.message?.let {
                Box(
                    contentAlignment = Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    RetrySection(it) {
                        viewModel.fetchArticleList()
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleEntry(
    entry: NewsArticle
) {
    Box(
        contentAlignment = Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .placeholder(R.drawable.loading)
                    .build(),
                contentDescription = entry.title,
                modifier = Modifier
                    .size(UiSize.SIZE_50)
            )

            Text(
                text = entry.title,
                maxLines = 2,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontSize = SP_SIZE_16,
                fontWeight = FontWeight(600),
                color = TextColor,
                modifier = Modifier
                    .padding(start = UiSize.SIZE_10)
            )
        }
    }
}


@Composable
private fun ArticleRow(
    rowIndex: Int,
    entries: List<NewsArticle>,
    viewModel:ArticleListViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.clickable {
        viewModel.sendIntent(
            ArticleListViewIntent.OnArticleClick(rowIndex.plus(1))
        )
    }) {
        Row {
            ArticleEntry(
                entry = entries[rowIndex]
            )
        }
        Spacer(modifier = Modifier.height(UiSize.SIZE_10))
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