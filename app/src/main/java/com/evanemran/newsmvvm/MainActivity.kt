package com.evanemran.newsmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import com.evanemran.newsmvvm.presentation.ExpandableSearchView
import com.evanemran.newsmvvm.presentation.ShimmerListItem
import com.evanemran.newsmvvm.presentation.utils.LayoutType
import com.evanemran.newsmvvm.presentation.utils.getCategories
import kotlin.random.Random
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toUpperCase
import coil.compose.AsyncImage
import com.evanemran.newsmvvm.presentation.NewsItemOverlay
import com.evanemran.newsmvvm.presentation.drawer.DrawerItem
import com.evanemran.newsmvvm.presentation.ui.theme.drawerColors
import com.evanemran.newsmvvm.presentation.utils.getCountries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: NewsViewModel by viewModels()
//    private var selectedButtonState: MutableState<String> = mutableStateOf("business")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadNewsData(Category.GENERAL.name)

        setContent {
            NewsMVVMTheme {
                val selectedButtonState = remember {
                    mutableStateOf(Category.GENERAL.name)
                }

                window.statusBarColor = getColor(R.color.white)

                fun onButtonClick(value: String) {
                    selectedButtonState.value = value
                    viewModel.loadNewsData(value)

                }

                Surface {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    var selectedItemIndex by rememberSaveable {
                        mutableIntStateOf(0)
                    }
                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet(
                                modifier = Modifier
                                    .background(Color.White),
                                drawerContainerColor = Color.White,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "NewsMVVM",
                                        color = Color.Red,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Get news update every hour!",
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                getCountries().forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        modifier = Modifier.padding(
                                            top = 8.dp,
                                            start = 8.dp,
                                            end = 8.dp
                                        ),
                                        label = {
                                            Text(text = item.name)
                                        },
                                        colors = drawerColors(),
                                        selected = index == selectedItemIndex,
                                        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                                        onClick = {
                                            selectedItemIndex = index
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        },
                                        icon = {
                                            ImageFlag(item.code)
                                        },
                                        badge = {
                                            Text(text = item.code.uppercase())
                                        },
                                    )
                                }
                                LazyColumn(
                                    userScrollEnabled = true,
                                    content = {
                                    getCountries().let {
                                        items(getCountries().size) { item ->
                                            NavigationDrawerItem(
                                                modifier = Modifier.padding(
                                                    top = 8.dp,
                                                    start = 8.dp,
                                                    end = 8.dp
                                                ),
                                                label = {
                                                    Text(text = it[item].name)
                                                },
                                                colors = drawerColors(),
                                                selected = item == selectedItemIndex,
                                                shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                                                onClick = {
                                                    selectedItemIndex = item
                                                    scope.launch {
                                                        drawerState.close()
                                                    }
                                                },
                                                icon = {
                                                    ImageFlag(it[item].code.uppercase())
                                                },
                                                badge = {
                                                    Text(text = it[item].code.uppercase())
                                                },
                                            )
                                        }
                                    }
                                })
                            }
                        },
                        drawerState = drawerState
                    ) {
                        Scaffold(
                            topBar = { AppBar(drawerState, scope) },
                            content = {
                                it
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 64.dp)
                                        .background(Color.White)
                                ) {
                                    if (!viewModel.state.isLoading && viewModel.state.newsInfo?.articles?.isNotEmpty() == true) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.White),
                                            verticalArrangement = Arrangement.Top,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            selectedButtonState.value.let {
                                                LazyRow(
                                                    modifier = Modifier.padding(8.dp),
                                                    content = {
                                                        items(getCategories()) { category ->
                                                            FilterButton(
                                                                category,
                                                                selectedButtonState.value,
                                                                onClick = ::onButtonClick
                                                            )
                                                        }
                                                    })
                                                LazyColumn(content = {
                                                    viewModel.state.newsInfo?.articles?.let {
                                                        items(it.size) { newsData ->
//                                                    val random: Int = Random.nextInt(2) + 1
                                                            val random: Int =
                                                                if (newsData % 3 == 0) 1 else if (newsData % 4 == 0) 3 else 2
                                                            when (random) {
                                                                1 -> {
                                                                    NewsItem(context = this@MainActivity ,article = it[newsData])
//                                                            ShimmerListItem(
//                                                                isLoading = viewModel.state.isLoading,
//                                                                contentAfterLoading = {
//                                                                    NewsItemInLine(article = it[newsData])
//                                                                },
//                                                                modifier = Modifier
//                                                                    .fillMaxWidth()
//                                                                    .padding(16.dp)
//                                                            )
                                                                }

                                                                2 -> {
                                                                    NewsItemInLine(context = this@MainActivity ,article = it[newsData])
                                                                }

                                                                3 -> {
                                                                    NewsItemOverlay(context = this@MainActivity ,article = it[newsData])
                                                                }
                                                            }
                                                        }
                                                    }
                                                })
                                            }
                                        }
                                    }
                                    if (viewModel.state.isLoading) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.White),
                                            verticalArrangement = Arrangement.Top,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            LazyRow(modifier = Modifier.padding(8.dp), content = {
                                                items(getCategories()) { category ->
                                                    FilterButton(
                                                        category,
                                                        selectedButtonState.value,
                                                        onClick = ::onButtonClick
                                                    )
                                                }
                                            })
                                            LazyColumn(
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                items(20) { index ->
                                                    val type: LayoutType =
                                                        if (index % 3 == 0) LayoutType.LARGE else if (index % 4 == 0) LayoutType.OVERLAY else LayoutType.LINEAR
                                                    ShimmerListItem(
                                                        isLoading = true,
                                                        type = type,
                                                        contentAfterLoading = {
                                                            Spacer(modifier = Modifier.width(10.dp))
                                                        },
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(8.dp)
                                                    )
                                                }
                                            }

//                                CircularProgressIndicator(
//                                    modifier = Modifier.align(Alignment.Center),
//                                    color = Color.Red
//                                )
                                        }
                                    }
                                    viewModel.state.error?.let { error ->
                                        Text(
                                            text = error,
                                            color = Color.Red,
                                            modifier = Modifier.align(Alignment.Center),
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
    }
}

@Composable
fun ImageFlag(imageUrl: String) {
    AsyncImage(
        modifier = Modifier
            .width(30.dp)
            .height(20.dp),
        model = "https://flagsapi.com/${imageUrl.uppercase()}/flat/64.png",
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        error = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "URL Image",
    )
}

@Composable
fun ExpandableCard(header: Unit) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                expanded = !expanded
            }
    ) {
        Column(
        ) {
            header
            if (expanded) {
                Text(
                    text = "Content Sample for Display on Expansion of Card",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun FilterButton(model: Category, selectedButtonState: String, onClick: (String) -> Unit) {
    var color: Color = Color.DarkGray
    if (selectedButtonState == model.name) {
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
fun AppBar(drawerState: DrawerState, scope: CoroutineScope) {
    TopAppBar(
        title = {
            Text(
                text = "NewsMVVM",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(
                onClick = {

                },
            ) {
                Icon(
                    Icons.Outlined.Settings,
                    tint = Color.Black,
                    contentDescription = ""
                )
            }

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

            //Need to fix boundaries
            /*ExpandableSearchView(
                searchDisplay = "Search",
                onSearchDisplayChanged = {},
                expandedInitially = true,
                onSearchDisplayClosed = {}

            )*/
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
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