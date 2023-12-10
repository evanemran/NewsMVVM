package com.evanemran.newsmvvm.presentation.ui.theme

import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val drawerRedLight = Color(0xFFEE2121)
val drawerWhite = Color(0xFFDDD9D9)
val black = Color(0xFF000000)
val transparentWhite = Color(0x72FFFFFF)
val transparentBlack = Color(0x72000000)


fun drawerColors(): MyDrawerColors {
    return MyDrawerColors()
}

class MyDrawerColors() :  NavigationDrawerItemColors{

    var selectedContainerColor: Color = drawerRedLight
    var unselectedContainerColor: Color = Color.White
    var selectedIconColor: Color = drawerWhite
    var unselectedIconColor: Color = black
    var selectedTextColor: Color = Color.White
    var unselectedTextColor: Color = black
    var selectedBadgeColor: Color = selectedTextColor
    var unselectedBadgeColor: Color = unselectedTextColor

    @Composable
    override fun badgeColor(selected: Boolean): State<Color> {
        return if (selected) rememberUpdatedState(newValue = selectedBadgeColor) else rememberUpdatedState(newValue = unselectedBadgeColor)
    }

    @Composable
    override fun containerColor(selected: Boolean): State<Color> {
        return if (selected) rememberUpdatedState(newValue = selectedContainerColor) else rememberUpdatedState(newValue = unselectedContainerColor)
    }

    @Composable
    override fun iconColor(selected: Boolean): State<Color> {
        return if (selected) rememberUpdatedState(newValue = selectedIconColor) else rememberUpdatedState(newValue = unselectedIconColor)
    }

    @Composable
    override fun textColor(selected: Boolean): State<Color> {
        return if (selected) rememberUpdatedState(newValue = selectedTextColor) else rememberUpdatedState(newValue = unselectedTextColor)
    }

}