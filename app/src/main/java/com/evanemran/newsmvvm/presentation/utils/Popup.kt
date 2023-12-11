package com.evanemran.newsmvvm.presentation.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerDefaults
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.evanemran.newsmvvm.domain.news.allSources
import com.evanemran.newsmvvm.presentation.ui.theme.drawerColors
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

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
                .fillMaxWidth()
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


/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupDatePicker(
    showDialog: Boolean,
    state: DateRangePickerState,
    onDismiss: (source: String) -> Unit,
) {
    if (showDialog) {
        DateRangePicker(state,
            modifier = Modifier.padding(0.dp).fillMaxWidth(),
            dateFormatter = DatePickerFormatter("yy MM dd", "yy MM dd", "yy MM dd"),
            dateValidator = dateValidator(),
            title = {
                Text(text = "", modifier = Modifier
                    .padding(16.dp))
            },
            headline = {
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(0.dp)) {
                    Box(Modifier.weight(1f)) {
                        (if(state.selectedStartDateMillis!=null) state.selectedStartDateMillis?.let { getFormattedDate(it) } else "Start Date")?.let { Text(text = it) }
                    }
                    Box(Modifier.weight(1f)) {
                        (if(state.selectedEndDateMillis!=null) state.selectedEndDateMillis?.let { getFormattedDate(it) } else "End Date")?.let { Text(text = it) }
                    }

                }
            },
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = Color.Blue,
                titleContentColor = Color.Black,
                headlineContentColor = Color.Black,
                weekdayContentColor = Color.Black,
                subheadContentColor = Color.Black,
                yearContentColor = Color.Green,
                currentYearContentColor = Color.Red,
                selectedYearContainerColor = Color.Red,
                disabledDayContentColor = Color.Gray,
                todayDateBorderColor = Color.Blue,
                dayInSelectionRangeContainerColor = Color.LightGray,
                dayInSelectionRangeContentColor = Color.White,
                selectedDayContainerColor = Color.Black
            )
        )


        *//*AlertDialog(
            onDismissRequest = { onDismiss("N/A") },
            containerColor = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp, bottom = 0.dp),
            title = { Text(text = "Select Date Range") },
            text = {
                DateRangePicker(state,
                    modifier = Modifier.padding(0.dp).fillMaxWidth(),
                    dateFormatter = DatePickerFormatter("yy MM dd", "yy MM dd", "yy MM dd"),
                    dateValidator = dateValidator(),
                    title = {
                        Text(text = "", modifier = Modifier
                            .padding(16.dp))
                    },
                    headline = {
                        Row(modifier = Modifier.fillMaxWidth()
                            .padding(0.dp)) {
                            Box(Modifier.weight(1f)) {
                                (if(state.selectedStartDateMillis!=null) state.selectedStartDateMillis?.let { getFormattedDate(it) } else "Start Date")?.let { Text(text = it) }
                            }
                            Box(Modifier.weight(1f)) {
                                (if(state.selectedEndDateMillis!=null) state.selectedEndDateMillis?.let { getFormattedDate(it) } else "End Date")?.let { Text(text = it) }
                            }

                        }
                    },
                    showModeToggle = false,
                    colors = DatePickerDefaults.colors(
                        containerColor = Color.Blue,
                        titleContentColor = Color.Black,
                        headlineContentColor = Color.Black,
                        weekdayContentColor = Color.Black,
                        subheadContentColor = Color.Black,
                        yearContentColor = Color.Green,
                        currentYearContentColor = Color.Red,
                        selectedYearContainerColor = Color.Red,
                        disabledDayContentColor = Color.Gray,
                        todayDateBorderColor = Color.Blue,
                        dayInSelectionRangeContainerColor = Color.LightGray,
                        dayInSelectionRangeContentColor = Color.White,
                        selectedDayContainerColor = Color.Black
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {onDismiss("N/A")}) {
                    Text(text = "OK")
                }
            }
        )*//*
    }
}*/

fun convertMillisToDate(millis: Long): String {
    val date = Date(millis)
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupDatePicker(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val today = LocalDate.now()
    val thirtyDaysAgo = today.minusDays(30)

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }

            ) {
                Text(text = "OK")
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Color.White,
            selectedDayContainerColor = Color.Red,
            selectedYearContainerColor = Color.Red,
            todayDateBorderColor = Color.Red,
            todayContentColor = Color.Red,
            selectedYearContentColor = Color.Red
        ),
        dismissButton = {
            TextButton(
                onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel", color = Color.Red)
            }
        },
        properties = DialogProperties()
    ) {
        DatePicker(
            colors = DatePickerDefaults.colors(
                selectedDayContentColor = Color.White,
                selectedDayContainerColor = Color.Red,
                todayDateBorderColor = Color.Red,
                todayContentColor = Color.Red
            ),
            state = datePickerState,
        )
    }
}


