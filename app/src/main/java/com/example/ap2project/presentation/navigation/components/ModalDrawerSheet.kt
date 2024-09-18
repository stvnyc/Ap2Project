package com.example.ap2project.presentation.navigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ap2project.presentation.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ModalDrawerSheet(
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    navHostController: NavHostController
) {
    ModalDrawerSheet {
        Box(
            modifier = Modifier
                .background(Color.Green)
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Text("")
        }
        Spacer(modifier = Modifier.padding(20.dp))
        NavigationDrawerItem(
            label = { Text(text = "Home") },
            selected = false,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
                navHostController.navigate(Screen.Home) {
                    popUpTo(0)
                }
            },
        )
        NavigationDrawerItem(
            label = { Text(text = "Prioridades") },
            selected = false,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )
            },
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
                navHostController.navigate(Screen.PrioridadList) {
                    popUpTo(0)
                }
            },
        )
    }
}