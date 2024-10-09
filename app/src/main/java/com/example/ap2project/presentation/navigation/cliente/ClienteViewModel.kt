package com.example.ap2project.presentation.navigation.cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ap2project.Data.repository.ClienteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getClientes()
    }

    fun save() {
        viewModelScope.launch {
            if (_uiState.value.nombre.isBlank() ||
                _uiState.value.telefono.isBlank() ||
                _uiState.value.celular.isBlank() ||
                _uiState.value.rnc.isBlank() ||
                _uiState.value.email.isBlank() ||
                _uiState.value.direccion.isBlank()
            ) {
                _uiState.update {
                    it.copy(message = "Todos los campos son requeridos")
                }
            } else {
                clienteRepository.saveCliente(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(message = "Agregado correctamente")
                }
                nuevo()
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

    fun delete() {
        viewModelScope.launch {
            clienteRepository.deleteCliente(_uiState.value.clienteId!!)
            nuevo()
        }
    }

    fun selectedCliente(clienteId: Int) {
        viewModelScope.launch {
            if (clienteId > 0) {
                val cliente = clienteRepository.getCliente(clienteId)
                _uiState.update {
                    it.copy(
                        clienteId = clienteId,
                        nombre = cliente.nombre ?: "",
                        telefono = cliente.telefono ?: "",
                        celular = cliente.celular ?: "",
                        rnc = cliente.rnc ?: "",
                        email = cliente.email ?: "",
                        direccion = cliente.direccion ?: ""
                    )
                }
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                clienteId = null,
                nombre = "",
                telefono = "",
                celular = "",
                rnc = "",
                email = "",
                direccion = ""
            )
        }
    }

    fun onNombreChange(nombre: String) {
        _uiState.update {
            it.copy(nombre = nombre)
        }
    }

    fun onTelefonoChange(telefono: String) {
        _uiState.update {
            it.copy(telefono = telefono)
        }
    }

    fun onCelularChange(celular: String) {
        _uiState.update {
            it.copy(celular = celular)
        }
    }

    fun onRncChange(rnc: String) {
        _uiState.update {
            it.copy(rnc = rnc)
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun onDireccionChange(direccion: String) {
        _uiState.update {
            it.copy(direccion = direccion)
        }
    }
}