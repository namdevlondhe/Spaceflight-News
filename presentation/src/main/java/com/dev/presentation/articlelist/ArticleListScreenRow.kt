package com.dev.presentation.articlelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dev.presentation.R
import com.dev.presentation.model.NewsArticle
import com.dev.presentation.ui.theme.TextColor
import com.dev.presentation.ui.theme.UiSize

@Composable
fun ArticleRow(
    rowIndex: Int,
    entries: List<NewsArticle>,
    viewModel: ArticleListViewModel = hiltViewModel()
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
fun ArticleEntry(
    entry: NewsArticle
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .placeholder(R.drawable.loading)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = entry.title,
                modifier = Modifier.fillMaxSize().weight(1f)
                    .size(UiSize.SIZE_100)
            )
            Column(
                modifier = Modifier
                    .padding(start = UiSize.SIZE_10).weight(2f)
            ) {
                Text(
                    text = entry.title,
                    maxLines = 2,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontSize = UiSize.SP_SIZE_16,
                    fontWeight = FontWeight(600),
                    color = TextColor
                )
                Text(
                    text = entry.newsSite,
                    maxLines = 1,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontSize = UiSize.SP_SIZE_14,
                    fontWeight = FontWeight(100),
                    color = TextColor,
                    modifier = Modifier.padding(top = UiSize.SIZE_5)
                )
                Text(
                    text = entry.publishedAt,
                    maxLines = 1,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontSize = UiSize.SP_SIZE_14,
                    fontWeight = FontWeight(100),
                    color = TextColor
                )
            }
        }
    }
}