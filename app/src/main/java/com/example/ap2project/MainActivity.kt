package com.example.ap2project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.ap2project.database.PrioridadDb
import com.example.ap2project.ui.theme.Ap2ProjectTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ap2project.entities.PrioridadEntity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    private lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration()
            .build()

        setContent {
            Ap2ProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        PrioridadScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun PrioridadScreen() {
        var descripcion by remember { mutableStateOf("") }
        var diasCompromiso by remember { mutableStateOf("") }
        var errorMessage: String? by remember { mutableStateOf("") }

        Scaffold { innerPadding ->

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
                            text = errorMessage.orEmpty(),
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
                            OutlinedButton(
                                onClick = {
                                    descripcion = ""
                                    diasCompromiso = ""
                                    errorMessage = null
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null
                                )
                                Text(text = "Nuevo")
                            }

                            val scope = rememberCoroutineScope()
                            OutlinedButton(
                                onClick = {
                                    val newDiasCompromiso = diasCompromiso.toIntOrNull()
                                    val descripcionExiste =
                                        runBlocking { findByDescription(descripcion) }
                                    errorMessage = when {
                                        descripcion.isBlank() -> "El campo descripción es necesario"
                                        newDiasCompromiso == null -> "El campo Días de Compromiso es necesario"
                                        newDiasCompromiso > 31 || newDiasCompromiso < 1 -> "Digite un rango entre 1 y 31"
                                        descripcionExiste != null -> "Esta descripción ya existe"
                                        else -> ""
                                    }
                                    if (errorMessage?.isEmpty() == true) {
                                        scope.launch {
                                            savePrioridad(
                                                PrioridadEntity(
                                                    descripcion = descripcion,
                                                    diasCompromiso = newDiasCompromiso
                                                )
                                            )
                                            descripcion = ""
                                            diasCompromiso = ""
                                            errorMessage = null
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Save Button"
                                )
                                Text(text = "Guardar")
                            }
                        }
                    }

                val lifecycleOwner = LocalLifecycleOwner.current
                val prioridadList by prioridadDb.prioridadDao().getall()
                    .collectAsStateWithLifecycle(
                        initialValue = emptyList(),
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED
                    )
                PrioridadListScreen(prioridadList)
            }
        }
    }

    @Composable
    fun PrioridadListScreen(prioridadList: List<PrioridadEntity>){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Lista de Prioridades",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(24.dp)
            )
            Row() {
                Text("Id", modifier = Modifier.weight(1f))
                Text("Descripción", modifier = Modifier.weight(2.5f))
                Text("Días de Compromiso", modifier = Modifier.weight(2.5f))
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(prioridadList){
                    PrioridadRow(it)
                }
            }
        }
    }

    @Composable
    private fun PrioridadRow(it: PrioridadEntity){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = it.prioridadId.toString()
            )
            Text(
                modifier = Modifier.weight(2.5f),
                text = it.descripcion
            )
            Text(
                modifier = Modifier.weight(2f),
                text = it.diasCompromiso.toString()
            )
        }
        HorizontalDivider()
    }

    private suspend fun savePrioridad(prioridad: PrioridadEntity) {
        prioridadDb.prioridadDao().save(prioridad)
    }

    private suspend fun findByDescription(descripcion: String): PrioridadEntity? {
        val existe = prioridadDb.prioridadDao().findByDescription(descripcion)
        return existe
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun PrioridadScreenPreview() {
        Ap2ProjectTheme {
            PrioridadScreen()
        }
    }
}