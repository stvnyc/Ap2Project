package com.example.ap2project.presentation.navigation.sistema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ap2project.Data.repository.SistemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SistemaViewModel @Inject constructor(
    private val sistemaRepository: SistemaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSistemas()
    }

    fun save() {
        viewModelScope.launch {
            if (_uiState.value.sistemaNombre.isBlank()
            ) {
                _uiState.update {
                    it.copy(message = "Todos los campos son requeridos")
                }
            } else {
                sistemaRepository.saveSistema(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(message = "Agregado correctamente")
                }
                nuevo()
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

    fun delete() {
        viewModelScope.launch {
            sistemaRepository.deleteSistema(_uiState.value.sistemaId!!)
            nuevo()
        }
    }

    fun selectedSistema(sistemaId: Int) {
        viewModelScope.launch {
            if (sistemaId > 0) {
                val sistema = sistemaRepository.getSistema(sistemaId)
                _uiState.update {
                    it.copy(
                        sistemaId = sistema.sistemaId,
                        sistemaNombre = sistema.sistemaNombre ?: ""
                    )
                }
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                sistemaId = null,
                sistemaNombre = ""
            )
        }
    }

    fun onSistemaNombreChange(sistemaNombre: String) {
        _uiState.update {
            it.copy(sistemaNombre = sistemaNombre)
        }
    }
}