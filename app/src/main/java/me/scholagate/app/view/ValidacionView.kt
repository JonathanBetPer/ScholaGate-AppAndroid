package me.scholagate.app.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import me.scholagate.app.R
import me.scholagate.app.components.CardAlumno
import me.scholagate.app.components.LectorNFCAnimacion
import me.scholagate.app.components.MainTitle
import me.scholagate.app.dtos.AlumnoDto
import me.scholagate.app.states.NFCState
import me.scholagate.app.ui.theme.SgRojoPastel
import me.scholagate.app.ui.theme.SgVerdePastel
import me.scholagate.app.viewModel.ScholaGateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValidacionView(navController: NavHostController, scholaGateViewModel: ScholaGateViewModel,){

    scholaGateViewModel.updateNFCState(
        scholaGateViewModel.uiNfcViewState.collectAsState().value.copy(
            NFCState = NFCState.ReadyToRead,
            alumno = AlumnoDto(),
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { MainTitle(title = "Validación",
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
        ContentValidacion(pad = it, scholaGateViewModel = scholaGateViewModel)
    }
}


@Composable
fun ContentValidacion(pad: PaddingValues, scholaGateViewModel: ScholaGateViewModel) {

    val listaAlumnos = scholaGateViewModel._listaAlumnos
    val listaGrupos = scholaGateViewModel._listaGrupos
    val selectedAlumno = remember { mutableStateOf<AlumnoDto?>(null) }
    val selectedGrupo = remember { mutableStateOf<String?>(null) }
    val mostrarDialog = remember { mutableStateOf(false) }
    val color = remember { mutableStateOf(Color.Black) }

    if (scholaGateViewModel._idAlumno != -1) {
        mostrarDialog.value = true
    }


    LectorNFCAnimacion(pad, colorBackgroud = color.value)


    if (mostrarDialog.value){
        Log.i("ValidacionView", "selectedAlumno: ${scholaGateViewModel._idAlumno}")

        selectedAlumno.value = listaAlumnos.find { it.id == scholaGateViewModel._idAlumno }
        selectedGrupo.value = listaGrupos[selectedAlumno.value?.idGrupo]

        if (selectedAlumno.value != null && selectedGrupo.value != null) {


            if (selectedAlumno.value?.isMayorDeEdad() == true) color.value = SgVerdePastel
            else color.value = SgRojoPastel


            Dialog(
                onDismissRequest = {
                    mostrarDialog.value = false
                    scholaGateViewModel.onValueIdAlumno(-1)
                    color.value = Color.Black
                }
            ) {
                CardAlumno(selectedAlumno.value!!, selectedGrupo.value!!) {
                    mostrarDialog.value = false
                    scholaGateViewModel.onValueIdAlumno(-1)
                    color.value = Color.Black
                }
            }
        }

    }
}