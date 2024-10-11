package com.example.ap2project.presentation.navigation.cliente

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
import com.example.ap2project.Data.remote.dto.ClienteDto
import com.example.ap2project.presentation.navigation.prioridad.SwipeToDeleteContainer

@Composable
fun ClienteListScreen(
    viewModel: ClienteViewModel = hiltViewModel(),
    goToClienteScreen: (Int) -> Unit,
    createCliente: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ClienteBodyListScreen(
        uiState,
        goToClienteScreen,
        createCliente,
        onDelete = viewModel::delete
    )
}

@Composable
fun ClienteBodyListScreen(
    uiState: UiState,
    goToClienteScreen: (Int) -> Unit,
    createCliente: () -> Unit,
    onDelete: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = createCliente,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Crear Cliente")
            }
        }
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                text = "Lista de Clientes",
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
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Nombre",
                    modifier = Modifier.weight(3f),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    "RNC",
                    modifier = Modifier.weight(1f),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp)
            ) {
                items(
                    uiState.clientes,
                    key = { it.clienteId!!}
                ) {
                    SwipeToDeleteContainer(
                        item = it,
                        onDelete = onDelete
                    ) { cliente ->
                        ClienteRow(cliente, goToClienteScreen)
                    }
                }
            }
        }
    }
}

@Composable
fun ClienteRow(
    it: ClienteDto,
    goToClienteScreen: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(14.dp)
            .clickable {
                goToClienteScreen(it.clienteId ?: 0)
            }
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = it.clienteId.toString(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(3f),
            text = it.nombre.toString(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(1f),
            text = it.rnc.toString(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
    HorizontalDivider()
}