package com.evanemran.newsmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.evanemran.newsmvvm.presentation.utils.ShimmerListItem
import com.evanemran.newsmvvm.presentation.utils.LayoutType
import com.evanemran.newsmvvm.presentation.utils.getCategories
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import coil.compose.AsyncImage
import com.evanemran.newsmvvm.presentation.NewsItemOverlay
import com.evanemran.newsmvvm.presentation.ui.theme.drawerColors
import com.evanemran.newsmvvm.presentation.ui.theme.drawerWhite
import com.evanemran.newsmvvm.presentation.utils.PopupDatePicker
import com.evanemran.newsmvvm.presentation.utils.PopupSources
import com.evanemran.newsmvvm.presentation.utils.getCountries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: NewsViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadNewsData(Category.GENERAL.name, "us", "N/A", "")

        setContent {
            NewsMVVMTheme {
                val selectedButtonState = remember {
                    mutableStateOf(Category.GENERAL.name)
                }
                val selectedCountryState = remember {
                    mutableStateOf("us")
                }
                val showSourceDialogState = remember { mutableStateOf(false) }

                val datePickerState = rememberDateRangePickerState()
                val showDatePickerDialogState = remember { mutableStateOf(false) }
                val bottomSheetState = rememberModalBottomSheetState()

                val selectedSourceState = remember {
                    mutableStateOf("N/A")
                }

                val searchQueryState = remember {
                    mutableStateOf("")
                }
                val searchActiveState = remember {
                    mutableStateOf(false)
                }

                window.statusBarColor = getColor(R.color.white)

                fun onButtonClick(value: String) {
                    selectedButtonState.value = value
                    viewModel.loadNewsData(value, selectedCountryState.value, "N/A", searchQueryState.value)

                }

                if (showSourceDialogState.value) {
                    PopupSources(showDialog = showSourceDialogState.value, selectedCategory = selectedButtonState.value, selectedCountry = selectedCountryState.value, selectedSourceState = selectedSourceState,
                        onDismiss = {newSource ->
                            if(newSource!="N/A") viewModel.loadNewsData(selectedButtonState.value, selectedCountryState.value, selectedSourceState.value, "")
                            selectedSourceState.value = newSource
                            showSourceDialogState.value = false

                        })
                }
                if (showDatePickerDialogState.value) {
                    PopupDatePicker(
                        onDateSelected = {
                                         //call service with params
                        },
                        onDismiss = {
                            showDatePickerDialogState.value = false
                        })
                }

                Surface {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    var selectedItemIndex by rememberSaveable {
                        mutableIntStateOf(0)
                    }
                    val configuration = LocalConfiguration.current
                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet(
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxWidth(0.7f),
                                drawerContainerColor = Color.White,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.news_mvvm),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .padding(0.dp)
                                            .width(100.dp)
                                            .height(100.dp)
                                    )
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
                                Text(
                                    text = "Choose a Country",
                                    modifier = Modifier
                                        .padding(start = 16.dp),
                                    color = Color.Red,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline
                                )
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
                                                    Text(text = it[item].name, maxLines = 1)
                                                },
                                                colors = drawerColors(),
                                                selected = getCountries()[item].code == selectedCountryState.value,
                                                shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                                                onClick = {
                                                    selectedCountryState.value = getCountries()[item].code
                                                    selectedItemIndex = item
                                                    scope.launch {
                                                        drawerState.close()
                                                        searchQueryState.value = ""
                                                        viewModel.loadNewsData(selectedButtonState.value, selectedCountryState.value, "N/A", "")
                                                    }
                                                },
                                                icon = {
                                                    ImageFlag(it[item].code.uppercase())
                                                },
                                                badge = {
                                                    Text(text = it[item].code.uppercase(), fontSize = 12.sp)
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
                            topBar = { AppBar(drawerState, scope, selectedCountryState, showSourceDialogState, showDatePickerDialogState) },
                            content = {
                                it
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
                                            SearchBar(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 10.dp, end = 10.dp),
                                                colors = SearchBarDefaults.colors(
                                                    containerColor = Color.White,
                                                    dividerColor = Color.White
                                                ),
                                                query = searchQueryState.value,
                                                onQueryChange = {
                                                    searchQueryState.value = it
                                                },
                                                onSearch = {
                                                    searchActiveState.value = false
                                                    //other params kept empty to get maximum results
                                                    viewModel.loadNewsData("", "", "N/A", searchQueryState.value)
                                                },
                                                active = searchActiveState.value,
                                                onActiveChange = {
                                                    searchActiveState.value = it
                                                },
                                                placeholder = {
                                                    Text(text = "Search")
                                                },
                                                leadingIcon = {
                                                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "")
                                                },
                                                trailingIcon = {
                                                    if(searchActiveState.value || searchQueryState.value.isNotEmpty()) {
                                                        Icon(
                                                            modifier = Modifier
                                                                .clickable {
                                                                    if(searchQueryState.value.isNotEmpty()) {
                                                                        searchQueryState.value = ""
                                                                    }
                                                                    else {
                                                                        searchActiveState.value = false
                                                                    }
                                                                },
                                                            imageVector = Icons.Rounded.Clear,
                                                            contentDescription = "Clear"
                                                        )
                                                    }
                                                },
                                                content = {}
                                            )
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
                                            if (!viewModel.state.isLoading && viewModel.state.newsInfo?.articles?.isNotEmpty() == true) {
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
                                            if (viewModel.state.isLoading) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .background(Color.White),
                                                    verticalArrangement = Arrangement.Top,
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
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
                                                }
                                            }
                                            viewModel.state.error?.let { error ->
                                                Column(
                                                    modifier = Modifier.fillMaxSize(),
                                                    verticalArrangement = Arrangement.Center,
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    Text(
                                                        modifier = Modifier
                                                            .padding(16.dp),
                                                        text = if (searchQueryState.value.isEmpty()) error else "No Data Found For ${searchQueryState.value}!",
                                                        color = Color.DarkGray,
                                                        textAlign = TextAlign.Center,
                                                    )
                                                    TextButton(
                                                        colors = ButtonDefaults.buttonColors(
                                                            containerColor = drawerWhite
                                                        ),
                                                        onClick = { /*TODO*/ })
                                                    {
                                                        Text(text = "Retry", color = Color.Red)
                                                    }
                                                }
                                            }
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
fun AppBar(
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedCountryState: MutableState<String>,
    showSourceDialogState: MutableState<Boolean>,
    showDatePickerDialogState: MutableState<Boolean>
) {
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
                    showSourceDialogState.value = true
                },
            ) {
                Icon(
                    Icons.Outlined.List,
                    tint = Color.Black,
                    contentDescription = ""
                )
            }

            IconButton(
                onClick = {
                    showDatePickerDialogState.value = true
                },
            ) {
                Icon(
                    Icons.Rounded.DateRange,
                    tint = Color.Black,
                    contentDescription = ""
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                },
            ) {
                ImageFlag(selectedCountryState.value)
            }
        },
    )
}