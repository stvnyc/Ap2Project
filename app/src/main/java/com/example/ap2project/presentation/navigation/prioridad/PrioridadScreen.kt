package com.example.ap2project.presentation.navigation.prioridad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PrioridadScreen(
    viewModel: PrioridadViewModel = hiltViewModel(),
    onGoToPrioridadListScreen: () -> Unit,
    prioridadId: Int
) {

    LaunchedEffect(key1 = prioridadId) {
        if (prioridadId > 0) {
            viewModel.selectedPrioridad(prioridadId)
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadBodyScreen(
        uiState = uiState,
        onDescripcionChange = viewModel::onDescripcionChange,
        onDiasCompromisoChange = viewModel::onDiasCompromisoChange,
        savePrioridad = viewModel::save,
        nuevaPrioridad = viewModel::nuevo,
        onGoToPrioridadListScreen = onGoToPrioridadListScreen,
        prioridadId = prioridadId
    )
}

@Composable
fun PrioridadBodyScreen(
    uiState: UiState,
    onDescripcionChange: (String) -> Unit,
    onDiasCompromisoChange: (String) -> Unit,
    savePrioridad: () -> Unit,
    nuevaPrioridad: () -> Unit,
    onGoToPrioridadListScreen: () -> Unit,
    prioridadId: Int
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onGoToPrioridadListScreen,
                modifier = Modifier.padding(16.dp)
            ) {
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
                    text = "Registro de Prioridades",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    label = { Text(text = "Descripción") },
                    value = uiState.descripcion,
                    onValueChange = onDescripcionChange,
                    modifier = Modifier.fillMaxWidth(),
                )

                OutlinedTextField(
                    label = { Text(text = "Días Compromiso") },
                    value = uiState.diasCompromiso?.toString() ?: "",
                    onValueChange = onDiasCompromisoChange,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Text(
                    text = uiState.errorMessage ?: "",
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
                    OutlinedButton(
                        onClick = nuevaPrioridad
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null
                        )
                        Text(text = "Nuevo")
                    }

                    OutlinedButton(
                        onClick = {
                            savePrioridad()
                            nuevaPrioridad()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )
                        Text(text = if (prioridadId > 0) "Editar" else "Registrar")
                    }
                }
            }
        }
    }
}