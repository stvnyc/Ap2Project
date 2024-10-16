package com.example.ap2project.presentation.navigation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ap2project.Data.dao.entities.TicketEntity
import com.example.ap2project.Data.repository.ClienteRepository
import com.example.ap2project.Data.repository.PrioridadRepository
import com.example.ap2project.Data.repository.SistemaRepository
import com.example.ap2project.Data.repository.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val clienteRepository: ClienteRepository,
    private val prioridadRepository: PrioridadRepository,
    private val sistemaRepository: SistemaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
        getClientes()
        getSistemas()
        getPrioridades()
    }

    fun save() {
        viewModelScope.launch {
            val errorMessages = mutableListOf<String>()

            if (_uiState.value.clienteId == null) {
                errorMessages.add("Debe seleccionar un cliente.")
            }

            if (_uiState.value.sistemaId == null) {
                errorMessages.add("Debe seleccionar un sistema.")
            }

            if (_uiState.value.prioridadId == null) {
                errorMessages.add("Debe seleccionar una prioridad.")
            }

            if (_uiState.value.solicitadoPor.isBlank()) {
                errorMessages.add("El campo 'Solicitado por' no puede estar vacío.")
            }

            if (_uiState.value.asunto.isBlank()) {
                errorMessages.add("El campo 'Asunto' no puede estar vacío.")
            }

            if (_uiState.value.descripcion.isBlank()) {
                errorMessages.add("El campo 'Descripción' no puede estar vacío.")
            }

            if (_uiState.value.date == null) {
                errorMessages.add("Debe seleccionar una fecha.")
            }

            if (errorMessages.isNotEmpty()) {
                _uiState.update {
                    it.copy(message = errorMessages.joinToString("\n"))
                }
            } else {
                // Guardar el ticket si todas las validaciones pasan
                ticketRepository.saveTicket(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(message = "Agregado correctamente")
                }
            }
        }
    }

    private fun getTickets() {
        viewModelScope.launch {
            val tickets = ticketRepository.getTickets()
            _uiState.update {
                it.copy(tickets = tickets)
            }
        }
    }

    private fun getClientes() {
        viewModelScope.launch {
            val clientes = clienteRepository.getClientes()
            _uiState.update {
                it.copy(clientes = clientes)
            }
        }
    }

    private fun getSistemas() {
        viewModelScope.launch {
            val sistemas = sistemaRepository.getSistemas()
            _uiState.update {
                it.copy(sistemas = sistemas)
            }
        }
    }

    private fun getPrioridades() {
        viewModelScope.launch {
            val prioridades = prioridadRepository.getPrioridades()
            _uiState.update {
                it.copy(prioridades = prioridades)
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            ticketRepository.deleteTicket(_uiState.value.ticketId!!)
            nuevo()
        }
    }

    fun selectedTicket(ticketId: Int) {
        viewModelScope.launch {
            if (ticketId > 0) {
                val ticket = ticketRepository.getTicket(ticketId)
                _uiState.update {
                    it.copy(
                        ticketId = ticket.ticketId,
                        date = ticket.date,
                        clienteId = ticket.clienteId,
                        sistemaId = ticket.sistemaId,
                        prioridadId = ticket.prioridadId,
                        solicitadoPor = ticket.solicitadoPor ?: "",
                        asunto = ticket.asunto ?: "",
                        descripcion = ticket.descripcion ?: "",
                    )
                }
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                ticketId = null,
                date = Date(),
                clienteId = null,
                sistemaId = null,
                prioridadId = null,
                solicitadoPor = "",
                asunto = "",
                descripcion = ""
            )
        }
    }

    fun onDateChange(date: Date) {
        _uiState.update {
            it.copy(date = date)
        }
    }

    fun onClienteIdChange(clienteId: Int) {
        _uiState.update {
            it.copy(clienteId = clienteId)
        }
    }

    fun onSistemaIdChange(sistemaId: Int) {
        _uiState.update {
            it.copy(sistemaId = sistemaId)
        }
    }

    fun onPrioridadIdChange(prioridadId: Int) {
        _uiState.update {
            it.copy(prioridadId = prioridadId)
        }
    }

    fun onSolicitadoPorChange(solicitadoPor: String) {
        _uiState.update {
            it.copy(solicitadoPor = solicitadoPor)
        }
    }

    fun onAsuntoChange(asunto: String) {
        _uiState.update {
            it.copy(asunto = asunto)
        }
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun convertMillisToDate(millis: Long): Date {
        return Date(millis)
    }
}