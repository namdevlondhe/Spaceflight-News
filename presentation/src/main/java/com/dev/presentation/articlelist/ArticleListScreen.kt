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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dev.presentation.R
import com.dev.presentation.model.NewsArticle

/**
 * This function is responsible to provide the UI for article list using compose
 */
@Composable
fun ArticleListScreen(
    callback: (id: Int) -> Unit
) {
    Surface(
        color = Color(0xFFDEEDED),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(start = 28.dp, end = 25.dp)) {

            Divider(
                modifier = Modifier
                    .width(318.dp)
                    .height(1.dp)
                    .background(color = Color(0xFF5D5F7E))
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.sub_title),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF5D5F7E),

                    )
            )

            Spacer(modifier = Modifier.height(16.dp))
            ArticleList(callback)
        }
    }
}

@Composable
fun ArticleList(
    itemClicked: (id: Int) -> Unit,
    viewModel: ArticleListViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.sendIntent(ArticleListViewIntent.LoadData)
    }

    val viewState =
        viewModel.stateSharedFlow.collectAsState(initial = ArticleListViewState.Loading)

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
                    ArticleRow(rowIndex = it, entries = articleList, itemClicked)
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
                    .data(entry.image_url)
                    .placeholder(R.drawable.loading)
                    .build(),
                contentDescription = entry.title,
                modifier = Modifier
                    .size(50.dp)
            )

            Text(
                text = entry.title,
                maxLines = 2,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF2E3156),
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }
    }
}


@Composable
fun ArticleRow(
    rowIndex: Int,
    entries: List<NewsArticle>,
    itemClicked: (id: Int) -> Unit
) {
    Column(modifier = Modifier.clickable { itemClicked.invoke(rowIndex+1) }) {
        Row {
            ArticleEntry(
                entry = entries[rowIndex]
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.button_retry))
        }
    }
}