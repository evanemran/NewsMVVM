package com.evanemran.newsmvvm.presentation.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evanemran.newsmvvm.domain.news.allSources
import com.evanemran.newsmvvm.presentation.ui.theme.drawerColors

@Composable
fun PopupSources(
    showDialog: Boolean,
    selectedCategory: String,
    selectedCountry: String,
    onDismiss: (source: String) -> Unit,
    selectedSourceState: MutableState<String>
) {
    if (showDialog) {
//        var newSelectedSource = selectedSourceState.value
        AlertDialog(
            onDismissRequest = { onDismiss("N/A") },
            containerColor = Color.White,
            modifier = Modifier
                .padding(top = 60.dp, bottom = 60.dp),
            title = { Text(text = "Select Source") },
            text = {
                   LazyColumn(
                       content = {
                           allSources(selectedCategory, selectedCountry).let {
                               items(it.size) { item->
                                   NavigationDrawerItem(
                                       modifier = Modifier.padding(
                                           top = 4.dp,
                                           start = 4.dp,
                                           end = 4.dp
                                       ),
                                       label = {
                                           Text(text = it[item].name, maxLines = 1)
                                       },
                                       colors = drawerColors(),
                                       selected = it[item].id == selectedSourceState.value,
                                       shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                                       onClick = {
                                           selectedSourceState.value = it[item].id
//                                           selectedCountryState.value = getCountries()[item].code
//                                           selectedItemIndex = item
//                                           scope.launch {
//                                               drawerState.close()
//                                               viewModel.loadNewsData(selectedButtonState.value, selectedCountryState.value)
//                                           }
                                       },
//                                       icon = {
//                                           ImageFlag(it[item].code.uppercase())
//                                       },
                                       badge = {
                                           Text(text = it[item].category.uppercase(), fontStyle = FontStyle.Italic, fontSize = 10.sp)
                                       },
                                   )
                               }
                           }
                       })
            },
            confirmButton = {
                TextButton(onClick = { onDismiss(selectedSourceState.value) }) {
                    Text(text = "OK")
                }
            }
        )
    }
}