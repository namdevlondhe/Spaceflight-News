package com.dev.presentation.articlelist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dev.presentation.R
import com.dev.presentation.model.NewsArticle
import com.dev.presentation.ui.theme.Roboto

@Composable
@Preview
fun Preview() {
    //ArticleListScreen()
}

/**
 * This function is responsible to provide the UI for article list using compose
 */
@Composable
fun ArticleListScreen(
    navController: NavController
) {
    Surface(
        color = Color(0xFFDEEDED),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(start = 28.dp, end = 25.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.title),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF2E3156),

                    letterSpacing = 1.8.sp,
                ),
                modifier = Modifier
                    .width(128.dp)
                    .height(35.dp)
            )
            Divider(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
                    .width(318.dp)
                    .height(1.dp)
                    .background(color = Color(0xFF5D5F7E))
            )

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
            ArticleList(navController = navController)
        }
    }
}

@Preview
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Companion.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, shape = RoundedCornerShape(size = 8.dp))
                .background(color = Color(0xFFC9DDE2), shape = RoundedCornerShape(size = 8.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && text.isNotEmpty()
                }
        )
    }
}

@Composable
fun ArticleList(
    navController: NavController,
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
                    ArticleRow(rowIndex = it, entries = articleList, navController = navController)
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
    entry: NewsArticle,
    navController: NavController
) {
    Box(
        contentAlignment = Center,
        modifier = Modifier
            .clickable {
                navController.navigate(
                    "article_detail_screen/${entry.title}"
                )
            }
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.url)
                    .placeholder(R.drawable.loading)
                    .build(),
                contentDescription = entry.title,
                modifier = Modifier.fillMaxWidth()
                    .size(120.dp)
                    .align(CenterHorizontally)
            )

            Text(
                text = entry.title,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF2E3156),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            )
            Text(
                text = entry.summary,
                fontFamily = Roboto,
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF2E3156),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun ArticleRow(
    rowIndex: Int,
    entries: List<NewsArticle>,
    navController: NavController
) {
    Column {
        Row {
            ArticleEntry(
                entry = entries[rowIndex],
                navController = navController
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