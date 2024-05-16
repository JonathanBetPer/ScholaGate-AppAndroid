package me.scholagate.app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import me.scholagate.app.R
import me.scholagate.app.components.AlertDialogPersonalizado
import me.scholagate.app.components.Buscador
import me.scholagate.app.components.CardAlumno
import me.scholagate.app.components.MainTitle
import me.scholagate.app.components.MiniCardAlumno
import me.scholagate.app.components.SpaceV
import me.scholagate.app.dtos.AlumnoDto
import me.scholagate.app.states.NFCState
import me.scholagate.app.viewModel.ScholaGateViewModel
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteNFCView(navController: NavHostController, scholaGateViewModel: ScholaGateViewModel,) {
    Scaffold(
        topBar = {
            TopAppBar(title = { MainTitle(title = "NFC Writer",
                color = MaterialTheme.colorScheme.onPrimary ) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceTint
                ),
                actions = {
                    Button(
                        onClick = {
                            navController.popBackStack()
                        }
                    ){
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = "Atrás")
                    }
                }
            )
        }
    ) {
        ContentWriteNFC(pad = it, scholaGateViewModel = scholaGateViewModel)
    }
}

@Composable
fun ContentWriteNFC(
    pad: PaddingValues,
    scholaGateViewModel: ScholaGateViewModel,
) {

    val listaAlumnos = scholaGateViewModel._listaAlumnos
    val listaGrupos = scholaGateViewModel._listaGrupos
    var listaAlumnosFiltrada: List<AlumnoDto>

    val textoBuscador = remember { mutableStateOf("") }

    if (textoBuscador.value.isNotEmpty()){
        listaAlumnosFiltrada = listaAlumnos.filter {
            it.nombre.lowercase().contains(textoBuscador.value.lowercase())
        }
    }else{
        listaAlumnosFiltrada = listaAlumnos
    }

    val showDialog = remember { mutableStateOf(false) }
    val showDialigCanceler = remember { mutableStateOf(false) }
    val selectedAlumno = remember { mutableStateOf<AlumnoDto?>(null) }
    val selectedGrupo = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(pad),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {

        Buscador(textoBuscador)

        SpaceV()

        Text(text = "Selecione un alumno:")

        SpaceV()

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(listaAlumnosFiltrada){
                item -> MiniCardAlumno(item, listaGrupos[item.idGrupo]?:"Sin grupo")
                {
                    selectedAlumno.value = item
                    selectedGrupo.value = listaGrupos[item.idGrupo]
                    showDialog.value = true

                    scholaGateViewModel.updateNFCState(
                        scholaGateViewModel.uiNfcViewState.value.copy(
                            NFCState = NFCState.ReadyToWrite(selectedAlumno.value!!),
                            alumno = selectedAlumno.value!!
                        )
                    )
                }
            }
        }

        if (showDialog.value) {

            Dialog(onDismissRequest = { showDialigCanceler.value = true }) {
                CardAlumno(selectedAlumno.value!!, selectedGrupo.value!!) {
                    showDialigCanceler.value = true
                }
            }

            if (showDialigCanceler.value){
                AlertDialogPersonalizado(
                    onDismissRequest = { showDialigCanceler.value = false },
                    onConfirmation = {
                        showDialigCanceler.value = false
                        showDialog.value = false
                    },
                dialogTitle = "¿Quieres cancelar la selección?")
            }

        }else{
            scholaGateViewModel.uiNfcViewState.collectAsState().value.copy(
                NFCState = NFCState.None,
                alumno = AlumnoDto()
            )
        }
    }
}