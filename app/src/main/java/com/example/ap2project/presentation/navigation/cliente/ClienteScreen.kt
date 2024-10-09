package com.example.ap2project.presentation.navigation.cliente

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
fun ClienteScreen(
    viewModel: ClienteViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    clienteId: Int
) {
    LaunchedEffect(key1 = clienteId) {
        if (clienteId > 0) {
            viewModel.selectedCliente(clienteId)
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ClienteBodyScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onNombreChange = viewModel::onNombreChange,
        onTelefonoChange = viewModel::onTelefonoChange,
        onCelularChange = viewModel::onCelularChange,
        onRncChange = viewModel::onRncChange,
        onEmailChange = viewModel::onEmailChange,
        onDireccionChange = viewModel::onDireccionChange,
        saveCliente = viewModel::save,
        nuevoCliente = viewModel::nuevo
    )
}

@Composable
fun ClienteBodyScreen(
    uiState: UiState,
    onNavigateBack: () -> Unit,
    onNombreChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onCelularChange: (String) -> Unit,
    onRncChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onDireccionChange: (String) -> Unit,
    saveCliente: () -> Unit,
    nuevoCliente: () -> Unit
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
                    text = "Registro de Clientes",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            OutlinedTextField(
                label = { Text(text = "Nombre") },
                value = uiState.nombre,
                onValueChange = onNombreChange,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text(text = "Telefono") },
                value = uiState.telefono,
                onValueChange = onTelefonoChange,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text(text = "Celular") },
                value = uiState.celular,
                onValueChange = onCelularChange,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text(text = "RNC") },
                value = uiState.rnc,
                onValueChange = onRncChange,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text(text = "Email") },
                value = uiState.email,
                onValueChange = onEmailChange,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text(text = "Direccion") },
                value = uiState.direccion,
                onValueChange = onDireccionChange,
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
                OutlinedButton(onClick = nuevoCliente) {
                    Icon(Icons.Default.AddCircle, contentDescription = null)
                    Text(text = "Nuevo")
                }

                OutlinedButton(onClick = saveCliente) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Text(text = "Guardar")
                }
            }
        }
    }
}