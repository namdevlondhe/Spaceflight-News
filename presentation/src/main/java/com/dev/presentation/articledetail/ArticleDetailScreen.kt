package com.dev.presentation.articledetail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dev.presentation.R
 import com.dev.presentation.articlelist.RetrySection
import com.dev.presentation.model.NewsArticle
import com.dev.presentation.ui.theme.BackgroundColor
import com.dev.presentation.ui.theme.SubTextColor
import com.dev.presentation.ui.theme.TextColor
import com.dev.presentation.ui.theme.UiSize
import com.dev.presentation.ui.theme.UiSize.Companion.SP_SIZE_30

/**
 * This composable function is responsible for creating detail screen for News Artile
 */
@Composable
fun ArticleDetailScreen(
    id: Int, viewModel: ArticleDetailViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.sendIntent(ArticleDetailViewIntent.LoadData(id))
    }
    val viewState =
        viewModel.stateFlow.collectAsState(initial = ArticleDetailViewState.Loading)

    when (viewState.value) {
        is ArticleDetailViewState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        is ArticleDetailViewState.Success -> {
            DetailDataScreen((viewState.value as ArticleDetailViewState.Success).data)
        }

        is ArticleDetailViewState.Error -> {
            (viewState.value as ArticleDetailViewState.Error).let {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    RetrySection(it.message) {
                        viewModel.sendIntent(ArticleDetailViewIntent.LoadData(id))
                    }
                }
            }
        }
    }
}

@Composable
fun DetailDataScreen(data: NewsArticle) {
    Surface(
        color = BackgroundColor, modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(start = UiSize.SIZE_10, end = UiSize.SIZE_10)) {
            Spacer(modifier = Modifier.height(UiSize.SIZE_5))

            Text(
                text = data.title, style = TextStyle(
                    fontSize = SP_SIZE_30,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(700),
                    color = TextColor,
                    letterSpacing = 1.8.sp,
                ), modifier = Modifier
                    .width(UiSize.SIZE_128)
                    .height(UiSize.SIZE_35)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(data.imageUrl)
                    .placeholder(R.drawable.loading).build(),
                contentDescription = data.title,
                modifier = Modifier.fillMaxWidth().padding(top = UiSize.SIZE_1)
            )

            WebView(url = data.url)
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun WebView(url: String) {
    var backEnabled by remember { mutableStateOf(false) }
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                    backEnabled = view.canGoBack()
                }
            }
            settings.javaScriptEnabled = true

            loadUrl(url)
        }
    })
}
