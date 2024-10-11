@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ap2project.presentation.navigation.ticket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun TicketScreen(
    viewModelTicket: TicketViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    ticketId: Int
) {
    LaunchedEffect(key1 = ticketId) {
        if (ticketId > 0) {
            viewModelTicket.selectedTicket(ticketId)
        }
    }

    val uiStateTicket by viewModelTicket.uiState.collectAsStateWithLifecycle()

    TicketBodyScreen(
        uiState = uiStateTicket,
        onNavigateBack = onNavigateBack,
        onDescripcionChange = viewModelTicket::onDescripcionChange,
        onAsuntoChange = viewModelTicket::onAsuntoChange,
        onSolicitadoPorChange = viewModelTicket::onSolicitadoPorChange,
        onClienteIdChange = viewModelTicket::onClienteIdChange,
        onSistemaIdChange = viewModelTicket::onSistemaIdChange,
        onPrioridadIdChange = viewModelTicket::onPrioridadIdChange,
        saveTicket = viewModelTicket::save,
        nuevoTicket = viewModelTicket::nuevo,
        onFechaChange = viewModelTicket::onDateChange
    )
}

@Composable
fun TicketBodyScreen(
    uiState: UiState,
    onNavigateBack: () -> Unit,
    onDescripcionChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onSolicitadoPorChange: (String) -> Unit,
    onClienteIdChange: (Int) -> Unit,
    onSistemaIdChange: (Int) -> Unit,
    onPrioridadIdChange: (Int) -> Unit,
    saveTicket: () -> Unit,
    nuevoTicket: () -> Unit,
    onFechaChange: (Date) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val formattedDate = uiState.date?.let { dateFormatter.format(it) } ?: ""

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
                    text = "Registro de Tickets",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            InputSelectAlertDialog(
                label = "Clientes",
                options = uiState.clientes,
                selectedOptionId = uiState.clienteId,
                onOptionSelected = onClienteIdChange,
                getId = { cliente -> cliente.clienteId!! },
                getLabel = { cliente -> cliente.nombre!! }
            )

            InputSelectAlertDialog(
                label = "Sistemas",
                options = uiState.sistemas,
                selectedOptionId = uiState.sistemaId,
                onOptionSelected = onSistemaIdChange,
                getId = { sistema -> sistema.sistemaId!! },
                getLabel = { sistema -> sistema.sistemaNombre!! }
            )

            InputSelectAlertDialog(
                label = "Prioridades",
                options = uiState.prioridades,
                selectedOptionId = uiState.prioridadId,
                onOptionSelected = onPrioridadIdChange,
                getId = { prioridad -> prioridad.prioridadId!! },
                getLabel = { prioridad -> prioridad.descripcion!! }
            )

            OutlinedTextField(
                label = { Text(text = "Solicitado por") },
                value = uiState.solicitadoPor,
                onValueChange = onSolicitadoPorChange,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                label = { Text(text = "Asunto") },
                value = uiState.asunto,
                onValueChange = onAsuntoChange,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.descripcion,
                onValueChange = onDescripcionChange,
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formattedDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha") },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Seleccionar Fecha")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            )

            if (showDatePicker) {
                DatePickerDialog(
                    onDateSelected = { date ->
                        onFechaChange(date)
                        showDatePicker = false
                    },
                    onDismissRequest = { showDatePicker = false }
                )
            }

            uiState.message?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(onClick = nuevoTicket) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Nuevo")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Nuevo")
                }

                OutlinedButton(onClick = saveTicket) {
                    Icon(Icons.Default.Check, contentDescription = "Guardar")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Guardar")
                }
            }
        }
    }
}

@Composable
fun <T> InputSelectAlertDialog(
    label: String,
    options: List<T>,
    selectedOptionId: Int?,
    onOptionSelected: (Int) -> Unit,
    getLabel: (T) -> String,
    getId: (T) -> Int
) {
    var showDialog by remember { mutableStateOf(false) }
    val selectedOptionLabel = options.firstOrNull { getId(it) == selectedOptionId }
        ?.let { getLabel(it) } ?: "Seleccione una opción"

    Column {
        OutlinedTextField(
            value = selectedOptionLabel,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Expandir")
                }
            }
        )

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Selecciona una opción") },
                text = {
                    LazyColumn {
                        items(options) { option ->
                            DropdownMenuItem(
                                text = { Text(getLabel(option)) },
                                onClick = {
                                    onOptionSelected(getId(option))
                                    showDialog = false
                                }
                            )
                        }
                    }
                },
                confirmButton = {
                    OutlinedButton(onClick = { showDialog = false }) {
                        Text("Cerrar")
                    }
                }
            )
        }
    }
}

@Composable
fun DatePickerDialog(
    onDateSelected: (Date) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                onDateSelected(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    LaunchedEffect(Unit) {
        datePickerDialog.setOnDismissListener { onDismissRequest() }
        datePickerDialog.show()
    }
}