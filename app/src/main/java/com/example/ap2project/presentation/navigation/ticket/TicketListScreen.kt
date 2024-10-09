package com.example.ap2project.presentation.navigation.ticket

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
import com.example.ap2project.Data.remote.dto.PrioridadDto
import com.example.ap2project.Data.remote.dto.TicketDto
import com.example.ap2project.presentation.navigation.prioridad.PrioridadViewModel
import com.example.ap2project.presentation.navigation.prioridad.SwipeToDeleteContainer
import kotlin.reflect.KFunction0

@Composable
fun TicketListScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    viewModelPrioridad: PrioridadViewModel = hiltViewModel(),
    goToTicketScreen: (Int) -> Unit,
    createTicket: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketBodyListScreen(
        uiState = uiState,
        goToTicketScreen = goToTicketScreen,
        createTicket = createTicket,
        onTicketSelected = viewModel::selectedTicket,
        onDelete = viewModel::delete
    )
}

@Composable
fun TicketBodyListScreen(
    uiState: UiState,
    goToTicketScreen: (Int) -> Unit,
    createTicket: () -> Unit,
    onTicketSelected: (Int) -> Unit,
    onDelete: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = createTicket,
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
                text = "Lista de Tickets",
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(24.dp),
            )
            Row(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(
                    "Cliente",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Prioridad",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Asunto",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Fecha",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {
                items(
                    uiState.tickets,
                    key = { it.prioridadId!! }
                ) { ticket ->
                    SwipeToDeleteContainer(
                        item = ticket,
                        onDelete = onDelete
                    ) {
                        TicketRow(ticket, uiState.prioridades, goToTicketScreen)
                    }
                }
            }
        }
    }
}

@Composable
fun TicketRow(
    ticket: TicketDto,
    prioridades: List<PrioridadDto>,
    goToTicketScreen: (Int) -> Unit
) {
    val prioridadDesc = prioridades.find { it.prioridadId == ticket.prioridadId }?.descripcion
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                goToTicketScreen(ticket.ticketId?: 0)
            }
            .padding(vertical = 14.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = ticket.clienteId.toString(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = prioridadDesc.toString(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = ticket.asunto.toString(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = ticket.date.toString(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}