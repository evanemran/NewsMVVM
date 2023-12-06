package com.evanemran.newsmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evanemran.newsmvvm.domain.news.Category
import com.evanemran.newsmvvm.presentation.NewsItem
import com.evanemran.newsmvvm.presentation.NewsItemInLine
import com.evanemran.newsmvvm.presentation.ui.theme.NewsMVVMTheme
import com.evanemran.newsmvvm.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.remember
import com.evanemran.newsmvvm.presentation.utils.getCategories
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: NewsViewModel by viewModels()
//    private var selectedButtonState: MutableState<String> = mutableStateOf("business")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadNewsData("general")

        setContent {
            NewsMVVMTheme {
                val selectedButtonState = remember {
                    mutableStateOf("general")
                }

                fun onButtonClick(value: String) {
                    selectedButtonState.value = value
                    viewModel.loadNewsData(value)
                }

                Scaffold(
                    topBar = { AppBar() },
                    content = {it
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 64.dp)
                                .background(Color.White)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                selectedButtonState.value.let {
                                    LazyRow(modifier = Modifier.padding(8.dp) ,content = {
                                        items(getCategories()) { category->
                                            FilterButton(category, selectedButtonState.value, onClick = ::onButtonClick)
                                        }
                                    })
                                    LazyColumn(content = {
                                        viewModel.state.newsInfo?.articles?.let {
                                            items(it.size) { newsData->
                                                val random: Int = Random.nextInt(2) + 1
                                                when(random) {
                                                    1-> {
                                                        NewsItem(article = it[newsData])
                                                    }
                                                    2-> {
                                                        NewsItemInLine(article = it[newsData])
                                                    }
                                                }
                                            }
                                        }
                                    })
                                }
                            }
                            if(viewModel.state.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            viewModel.state.error?.let { error ->
                                Text(
                                    text = error,
                                    color = Color.Red,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FilterButton(model: Category, selectedButtonState: String, onClick: (String) -> Unit) {
    var color: Color = Color.DarkGray
    if(selectedButtonState == model.name) {
        color = Color.Red
    }
    Button(
        modifier = Modifier
            .padding(vertical = 0.dp, horizontal = 4.dp)
            .clip(shape = RoundedCornerShape(0.dp)),
        onClick = {
            onClick(model.name)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Text(
            text = model.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        title = { Text(text = "NewsMVVM", fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
        actions = {
            IconButton(
                onClick = {

                },
            ) {
                Icon(
                    Icons.Rounded.Search,
                    tint = Color.Black,
                    contentDescription = ""
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {

//                    scope.launch {
//                        scaffoldState.drawerState.open()
//                    }
                },
            ) {
                Icon(
                    Icons.Rounded.Menu,
                    tint = Color.Red,
                    contentDescription = ""
                )
            }
        },
    )
}