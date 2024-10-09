package com.example.ap2project.presentation.navigation.prioridad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ap2project.Data.remote.dto.PrioridadDto
import com.example.ap2project.Data.repository.PrioridadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrioridadViewModel @Inject constructor(
    private val prioridadRepository: PrioridadRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPrioridades()
    }

    fun save() {
        viewModelScope.launch {
            if (_uiState.value.descripcion.isBlank() ||
                _uiState.value.diasCompromiso == null ||
                _uiState.value.diasCompromiso!! < 1
            ) {
                _uiState.update {
                    it.copy(message = "Todos los campos son requeridos")
                }
            }
            else {
                prioridadRepository.savePrioridad(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(message = "Agregado correctamente")
                }
                nuevo()
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

    suspend fun getAll(): List<PrioridadDto> {
        return prioridadRepository.getPrioridades()
    }

    fun delete() {
        viewModelScope.launch {
            prioridadRepository.deletePrioridad(_uiState.value.prioridadId!!)
            nuevo()
        }
    }

    fun selectedPrioridad(prioridadId: Int) {
        viewModelScope.launch {
            if (prioridadId > 0) {
                val prioridad = prioridadRepository.getPrioridad(prioridadId)
                _uiState.update {
                    it.copy(
                        prioridadId = prioridad.prioridadId,
                        descripcion = prioridad.descripcion ?: "",
                        diasCompromiso = prioridad.diasCompromiso
                    )
                }
            }
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                prioridadId = null,
                descripcion = "",
                diasCompromiso = null
            )
        }
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun onDiasCompromisoChange(diasCompromiso: String) {
        val dias = diasCompromiso.toIntOrNull()
        _uiState.update {
            it.copy(diasCompromiso = dias)
        }
    }

    fun onPrioridadIdChange(prioridadId: Int) {
        _uiState.update {
            it.copy(prioridadId = prioridadId)
        }
    }
}