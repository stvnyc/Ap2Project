package com.example.ap2project.presentation.navigation.prioridad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ap2project.Data.dao.entities.PrioridadEntity
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
                    it.copy(errorMessage = "Todos los campos son requeridos")
                }
            } else (
                    prioridadRepository.save(_uiState.value.toEntity())
                    )
        }
    }

    private fun getPrioridades() {
        viewModelScope.launch {
            prioridadRepository.getPrioridades().collect { prioridades ->
                _uiState.update {
                    it.copy(prioridades = prioridades)
                }
            }
        }
    }

    fun delete(prioridad: PrioridadEntity) {
        viewModelScope.launch {
            prioridadRepository.delete(prioridad)
        }
    }

    fun selectedPrioridad(prioridadId: Int) {
        viewModelScope.launch {
            if (prioridadId > 0) {
                val prioridad = prioridadRepository.getPrioridad(prioridadId)
                _uiState.update {
                    it.copy(
                        prioridadId = prioridad?.prioridadId,
                        descripcion = prioridad?.descripcion ?: "",
                        diasCompromiso = prioridad?.diasCompromiso
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
                diasCompromiso = null,
                errorMessage = null
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