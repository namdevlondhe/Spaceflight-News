package com.dev.presentation.artistdetail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dev.presentation.R
import com.dev.presentation.model.NewsArticle
import com.dev.presentation.ui.theme.BackgroundColor
import com.dev.presentation.ui.theme.SubTextColor
import com.dev.presentation.ui.theme.TextColor

@Composable
fun ArticleDetailScreen(
    id: Int, viewModel: ArticleDetailViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.sendIntent(ArticleDetailViewIntent.LoadData(id))
    }
    val viewState =
        viewModel.stateSharedFlow.collectAsState(initial = ArticleDetailViewState.Loading)

    when (viewState.value) {
        is ArticleDetailViewState.Loading -> {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }

        is ArticleDetailViewState.Success -> {
            DetailDataScreen((viewState.value as ArticleDetailViewState.Success).data)
        }

        is ArticleDetailViewState.Error -> {}
    }
}

@Composable
fun DetailDataScreen(data: NewsArticle) {
    Surface(
        color = BackgroundColor, modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(start = 28.dp, end = 25.dp)) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = data.title, style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(700),
                    color = TextColor,
                    letterSpacing = 1.8.sp,
                ), modifier = Modifier
                    .width(128.dp)
                    .height(35.dp)
            )
            Divider(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
                    .width(318.dp)
                    .height(1.dp)
                    .background(color = SubTextColor)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(data.image_url)
                    .placeholder(R.drawable.loading).build(),
                contentDescription = data.title,
                modifier = Modifier.fillMaxWidth()
            )

            WebView(url = data.url)
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(url: String) {
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
