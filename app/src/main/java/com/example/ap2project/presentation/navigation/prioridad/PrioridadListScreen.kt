@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ap2project.presentation.navigation.prioridad

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ap2project.Data.dao.entities.PrioridadEntity
import kotlinx.coroutines.delay

@Composable
fun PrioridadListScreen(
    viewModel: PrioridadViewModel = hiltViewModel(),
    goToPrioridadScreen: (Int) -> Unit,
    createPrioridad: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadBodyListScreen(
        uiState,
        goToPrioridadScreen,
        createPrioridad,
        onDelete = viewModel::delete
    )
}

@Composable
fun PrioridadBodyListScreen(
    uiState: UiState,
    goToPrioridadScreen: (Int) -> Unit,
    createPrioridad: () -> Unit,
    onDelete: (PrioridadEntity) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = createPrioridad,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Crear Prioridad")
            }
        }
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                text = "Lista de Prioridades",
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(24.dp)
            )
            Row(
                modifier = Modifier.padding(14.dp)
            ) {
                Text(
                    "Id",
                    modifier = Modifier.weight(1f),
                    fontSize = 20.sp
                )
                Text(
                    "Descripción",
                    modifier = Modifier
                        .weight(1.5f)
                        .weight(2.5f),
                    fontSize = 20.sp
                )
                Text(
                    "Días",
                    modifier = Modifier
                        .weight(0.5f)
                        .weight(2.5f),
                    fontSize = 20.sp
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp)
            ) {
                items(
                    uiState.prioridades,
                    key = {it.prioridadId!! }
                ) {
                    SwipeToDeleteContainer(
                        item = it,
                        onDelete = onDelete
                    ) { prioridad ->
                        PrioridadRow(prioridad, goToPrioridadScreen)
                    }
                }
            }
        }
    }
}

@Composable
private fun PrioridadRow(
    it: PrioridadEntity,
    goToPrioridadScreen:(Int) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(14.dp)
            .clickable {
                goToPrioridadScreen(it.prioridadId ?: 0)
            }
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = it.prioridadId.toString(),
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier.weight(3f),
            text = it.descripcion,
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier.weight(0.5f),
            text = it.diasCompromiso.toString(),
            fontSize = 18.sp
        )
    }
    HorizontalDivider()
}

@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else
                false
        }
    )
    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }
    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(state)
            },
            content = {content(item)}
        )
    }
}

@Composable
fun DeleteBackground(
    swipeToDismissBoxState: SwipeToDismissBoxState
) {
    val color = if (swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        Color.Red
    } else Color.Transparent
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}