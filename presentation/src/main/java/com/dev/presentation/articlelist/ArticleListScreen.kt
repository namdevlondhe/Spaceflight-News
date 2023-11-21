package com.dev.presentation.articlelist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dev.domain.model.Article
import com.dev.presentation.ui.theme.Roboto
import com.dev.presentation.R

@Composable
@Preview
fun Preview(){
    //ArticleListScreen()
}
/**
 * This function is responsible to provide the UI for article list using compose
 */
@Composable
fun ArticleListScreen(
    navController: NavController
){
    Surface(
        color = Color(0xFFDEEDED),
        modifier = Modifier.fillMaxSize()
    ){
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
            Divider(modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp)
                .width(318.dp)
                .height(1.dp)
                .background(color = Color(0xFF5D5F7E)))

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
    onSearch:(String) -> Unit = {}
){
    var text by remember{
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
        /*if(isHintDisplayed){
            Text(
                text = stringResource(id = R.string.hint_name_or_number),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0x805D5F7E),
                )
            )
        }*/
    }
}
@Composable
fun ArticleList(
    navController: NavController,
    viewModel: ArticleListViewModel = hiltViewModel()
){
    val articleList by remember { viewModel.articleList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    LazyColumn {
        val itemCount = if(articleList.size % 2 == 0){
            articleList.size / 2
        } else{
            articleList.size / 2 + 1
        }
        items(articleList.size){
            if(it >= itemCount - 1 && !endReached && !isLoading){
                LaunchedEffect(key1 = true){
                    viewModel.getArticleList()
                }
            }
            ArticleRow(rowIndex = it, entries = articleList, navController = navController)
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        if(loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.getArticleList()
            }
        }
    }
}

@Composable
fun ArticleEntry(
    entry: Article,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ArticleListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    val stroke = Stroke(width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    Box(
        contentAlignment = Center,
        modifier = modifier
            .aspectRatio(0.7f)
            .drawBehind {
                drawRoundRect(
                    color = Color(0xFF2E3156), style = stroke,
                    cornerRadius = CornerRadius(10f, 10f)
                )
            }
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    ),
                )
            )
            .clickable {
                navController.navigate(
                    "article_detail_screen/${dominantColor.toArgb()}/${entry.title}"
                )
            }
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.url)
                    //.placeholder(R.drawable.loading)
                    .build(),
                contentDescription = entry.title,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
                onSuccess = { success ->
                    val drawable = success.result.drawable
                    /*viewModel.calcDominantColor(drawable) { color ->
                        dominantColor = color
                    }*/
                }
            )
            // Remaining Task
            // Choose dominant color
            Text(
                text = entry.title,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF2E3156),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            )
            Text(
                text = String.format("%03d", entry.summary),
                fontFamily = Roboto,
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF2E3156),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun ArticleRow(
    rowIndex: Int,
    entries: List<Article>,
    navController: NavController
) {
    Column {
        Row {
            ArticleEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if(entries.size >= rowIndex * 2 + 2) {
                ArticleEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
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