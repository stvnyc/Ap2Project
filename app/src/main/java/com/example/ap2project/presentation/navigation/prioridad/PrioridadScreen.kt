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
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ap2project.database.PrioridadDb
import com.example.ap2project.entities.PrioridadEntity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun PrioridadScreen(
    onGoToPrioridadListScreen: () -> Unit,
    prioridadDb: PrioridadDb,
    prioridadId: Int
) {
    var descripcion by remember { mutableStateOf("") }
    var diasCompromiso by remember { mutableStateOf("") }
    var message: String? by remember { mutableStateOf("") }

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
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    modifier = Modifier.fillMaxWidth(),
                )

                OutlinedTextField(
                    label = { Text(text = "Días Compromiso") },
                    value = diasCompromiso,
                    onValueChange = { diasCompromiso = it },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Text(
                    text = message.orEmpty(),
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
                    LaunchedEffect(prioridadId) {
                        if (prioridadId > 0) {
                            val prioridadExiste = runBlocking {
                                prioridadDb.prioridadDao().find(prioridadId)
                            }
                            if (prioridadExiste != null) {
                                descripcion = prioridadExiste.descripcion
                                diasCompromiso = prioridadExiste.diasCompromiso.toString()
                            }
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            descripcion = ""
                            diasCompromiso = ""
                            message = null
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null
                        )
                        Text(text = "Nuevo")
                    }

                    val scope = rememberCoroutineScope()
                    val newDiasCompromiso = diasCompromiso.toIntOrNull()
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                val descripcionExiste = prioridadDb.prioridadDao().findByDescription(descripcion)
                                message = when {
                                    descripcion.isBlank() -> "El campo descripción es necesario"
                                    newDiasCompromiso == null -> "El campo Días de Compromiso es necesario"
                                    newDiasCompromiso > 31 || newDiasCompromiso < 1 -> "Digite un número entre 1 y 31"
                                    descripcionExiste != null -> "Esta descripción ya existe"
                                    else -> ""
                                }

                                if (message.isNullOrEmpty()) {
                                    if (prioridadId > 0) {
                                        prioridadDb.prioridadDao().update(
                                            PrioridadEntity(
                                                prioridadId = prioridadId,
                                                descripcion = descripcion,
                                                diasCompromiso = newDiasCompromiso
                                            )
                                        )
                                        message = "Editado!"
                                    } else {
                                        prioridadDb.prioridadDao().save(
                                            PrioridadEntity(
                                                descripcion = descripcion,
                                                diasCompromiso = newDiasCompromiso
                                            )
                                        )
                                        message = "Guardado!"
                                    }
                                    descripcion = ""
                                    diasCompromiso = ""
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null
                        )
                        Text(text = if (prioridadId > 0) "Actualizar" else "Guardar")
                    }
                    if (prioridadId > 0){
                        OutlinedButton(
                            onClick = {
                                scope.launch{
                                    prioridadDb.prioridadDao().delete(
                                        PrioridadEntity(
                                            prioridadId = prioridadId,
                                            descripcion = descripcion,
                                            diasCompromiso = newDiasCompromiso
                                        )
                                    )
                                }
                                descripcion = ""
                                diasCompromiso = ""
                                message = "Eliminado!"
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                            Text(text = "Eliminar", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}
