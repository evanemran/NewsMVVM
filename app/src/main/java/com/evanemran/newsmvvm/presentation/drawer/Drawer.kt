package com.evanemran.newsmvvm.presentation.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Drawer(drawerState: DrawerState) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Home",
                    modifier = Modifier.padding(16.dp),
                )
                Text(
                    text = "Settings",
                    modifier = Modifier.padding(16.dp)
                        .clickable {

                        },
                )
            }
        }
    ) {
        
    }
}