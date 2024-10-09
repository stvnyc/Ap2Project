package com.example.ap2project.presentation.navigation.sistema

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check

import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SistemaScreen(
    viewModel: SistemaViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    sistemaId: Int
) {
    LaunchedEffect(key1 = sistemaId) {
        if (sistemaId > 0) {
            viewModel.selectedSistema(sistemaId)
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SistemaBodyScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onSistemaNombreChange = viewModel::onSistemaNombreChange,
        saveSistema = viewModel::save,
        nuevoSistema = viewModel::nuevo
    )
}

@Composable
fun SistemaBodyScreen(
    uiState: UiState,
    onNavigateBack: () -> Unit,
    onSistemaNombreChange: (String) -> Unit,
    saveSistema: () -> Unit,
    nuevoSistema: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateBack) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Volver")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Registro de Sistemas",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            OutlinedTextField(
                label = { Text(text = "Nombre") },
                value = uiState.sistemaNombre,
                onValueChange = onSistemaNombreChange,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = uiState.message ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(onClick = nuevoSistema) {
                    Icon(Icons.Default.AddCircle, contentDescription = null)
                    Text(text = "Nuevo")
                }

                OutlinedButton(onClick = saveSistema) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Text(text = "Guardar")
                }
            }
        }
    }
}