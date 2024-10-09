package com.example.ap2project.presentation.navigation.sistema

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ap2project.Data.remote.dto.SistemaDto
import com.example.ap2project.presentation.navigation.prioridad.SwipeToDeleteContainer

@Composable
fun SistemaListScreen(
    viewModel: SistemaViewModel = hiltViewModel(),
    goToSistemaScreen: (Int) -> Unit,
    createSistema: () -> Unit,
    onDelete: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SistemaBodyListScreen(
        uiState = uiState,
        goToSistemaScreen = goToSistemaScreen,
        createSistema = createSistema,
        onSistemaSelected = viewModel::selectedSistema,
        onDelete = onDelete
    )
}

@Composable
fun SistemaBodyListScreen(
    uiState: UiState,
    goToSistemaScreen: (Int) -> Unit,
    createSistema: () -> Unit,
    onSistemaSelected: (Int) -> Unit,
    onDelete: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = createSistema,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Lista de Sistemas",
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(24.dp)
            )
            Row(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(
                    "Id",
                    modifier = Modifier
                        .weight(1f),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Nombre",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                items(
                    uiState.sistemas,
                    key = { it.sistemaId!! }
                ) {
                    SwipeToDeleteContainer(
                        item = it,
                        onDelete = onDelete
                    ) { sistema ->
                        SistemaRow(sistema, goToSistemaScreen)
                    }
                }
            }
        }
    }
}

@Composable
fun SistemaRow(
    it: SistemaDto,
    goToSistemaScreen: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(14.dp)
            .clickable {
                goToSistemaScreen(it.sistemaId?: 0)
            }
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = it.sistemaId.toString(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = it.sistemaNombre,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
    HorizontalDivider()
}