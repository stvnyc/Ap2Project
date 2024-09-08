package com.example.ap2project.presentation.navigation.prioridad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ap2project.entities.PrioridadEntity

@Composable
fun PrioridadListScreen(
    prioridadList: List<PrioridadEntity>,
    createPrioridad: () -> Unit,
    goToPrioridadScreen: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = createPrioridad,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Crear Prioridad")
            }
        }
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                text = "Lista de Prioridades",
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(24.dp)
            )
            Row(
                modifier = Modifier.padding(14.dp)
            ) {
                Text(
                    "Id",
                    modifier = Modifier.weight(1f),
                    fontSize = 20.sp
                )
                Text(
                    "Descripción",
                    modifier = Modifier.weight(2.5f),
                    fontSize = 20.sp
                )
                Text(
                    "Días de Compromiso",
                    modifier = Modifier.weight(2.5f),
                    fontSize = 20.sp
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp)
            ) {
                items(prioridadList){
                    PrioridadRow(it,goToPrioridadScreen)
                }
            }
        }
    }
}

@Composable
private fun PrioridadRow(
    it: PrioridadEntity,
    goToPrioridadScreen:(Int) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(14.dp)
            .clickable {
                goToPrioridadScreen(it.prioridadId?:0)
            }
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = it.prioridadId.toString(),
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier.weight(1f),
            text = it.descripcion,
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier.weight(1f),
            text = it.diasCompromiso.toString(),
            fontSize = 18.sp
        )
    }
    HorizontalDivider()
}