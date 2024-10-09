@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ap2project.presentation.navigation.ticket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ap2project.Data.remote.dto.PrioridadDto
import com.example.ap2project.presentation.navigation.prioridad.PrioridadViewModel
import kotlinx.coroutines.delay
import java.util.Date

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
        onClienteIdChange = viewModelTicket::onClienteIdChange,
        onSistemaIdChange = viewModelTicket::onSistemaIdChange,
        onPrioridadIdChange = viewModelTicket::onPrioridadIdChange,
        saveTicket = viewModelTicket::save,
        nuevoTicket = viewModelTicket::nuevo,
        onFechaChange = viewModelTicket::onDateChange,
        convertMillisToDate = viewModelTicket::convertMillisToDate
    )
}

@Composable
fun TicketBodyScreen(
    uiState: UiState,
    onNavigateBack: () -> Unit,
    onDescripcionChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onClienteIdChange: (Int) -> Unit,
    onSistemaIdChange: (Int) -> Unit,
    onPrioridadIdChange: (Int) -> Unit,
    convertMillisToDate: (Long) -> Date,
    saveTicket: () -> Unit,
    nuevoTicket: () -> Unit,
    onFechaChange: (Date) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { selectedDateMillis ->
            val selectedDate = convertMillisToDate(selectedDateMillis)
            onFechaChange(selectedDate)
        }
    }

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

            InputSelect(
                label = "Clientes",
                options = uiState.clientes,
                onOptionSelected = onClienteIdChange,
                getId = { cliente -> cliente.clienteId!! },
                getLabel = { cliente -> cliente.nombre!! }
            )
            InputSelect(
                label = "Sistemas",
                options = uiState.sistemas,
                onOptionSelected = onSistemaIdChange,
                getId = { sistema -> sistema.sistemaId!! },
                getLabel = { sistema -> sistema.sistemaNombre!! }
            )
            InputSelect(
                label = "Prioridades",
                options = uiState.prioridades,
                onOptionSelected = onPrioridadIdChange,
                getId = { prioridad -> prioridad.prioridadId!! },
                getLabel = { prioridad -> prioridad.descripcion!! }
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
                value = uiState.date?.toString() ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha") },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = !showDatePicker }) {
                        Icon(Icons.Default.DateRange, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(64.dp)
            )

            if (showDatePicker) {
                DatePicker(state = datePickerState)
            }

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
                OutlinedButton(onClick = nuevoTicket) {
                    Icon(Icons.Default.AddCircle, contentDescription = null)
                    Text(text = "Nuevo")
                }

                OutlinedButton(onClick = saveTicket) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Text(text = "Guardar")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> InputSelect(
    label: String,
    options: List<T>,
    onOptionSelected: (Int) -> Unit,
    getLabel: (T) -> String,
    getId: (T) -> Int
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(getLabel(option)) },
                    onClick = {
                        selectedOption = getLabel(option)
                        onOptionSelected(getId(option))
                        expanded = false
                    }
                )
            }
        }

    }
}
