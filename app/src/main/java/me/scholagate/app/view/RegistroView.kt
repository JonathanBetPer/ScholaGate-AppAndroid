package me.scholagate.app.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import me.scholagate.app.R
import me.scholagate.app.components.BotonCambioSeleccion
import me.scholagate.app.components.BotonPrincipal
import me.scholagate.app.components.MainTitle
import me.scholagate.app.components.MiniCardAlumno
import me.scholagate.app.components.MiniNFCAnimacion
import me.scholagate.app.components.TextFieldGenerico
import me.scholagate.app.dtos.AlumnoDto
import me.scholagate.app.dtos.ReporteDto
import me.scholagate.app.states.NFCState
import me.scholagate.app.viewModel.ScholaGateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroView(navController: NavHostController, scholaGateViewModel: ScholaGateViewModel,){

    scholaGateViewModel.updateNFCState(
        scholaGateViewModel.uiNfcViewState.collectAsState().value.copy(
            NFCState = NFCState.ReadyToRead,
            alumno = AlumnoDto(),
        )
    )
    scholaGateViewModel.onValueReporte(ReporteDto())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    MainTitle(
                        title = "Crear Reporte",
                    )
                },
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
        ContentRegistro(pad = it, scholaGateViewModel = scholaGateViewModel, navController)
    }
}



@Composable
fun ContentRegistro(
    pad: PaddingValues,
    scholaGateViewModel: ScholaGateViewModel,
    navController: NavHostController
) {

    val tipoRegistro = remember { mutableStateOf(true) }
    val motivoValue = remember { mutableStateOf("") }
    val errorMotivo = remember { mutableStateOf(false) }
    val errorAlumno = remember { mutableStateOf(false) }
    val listaAlumnos = scholaGateViewModel._listaAlumnos
    val listaGrupos = scholaGateViewModel._listaGrupos
    val selectedAlumno = remember { mutableStateOf<AlumnoDto?>(null) }
    val selectedGrupo = remember { mutableStateOf<String?>(null) }
    val mostrarDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(pad)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp)
                .align(CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            BotonCambioSeleccion(
                "Entrada",
                !tipoRegistro.value,
            ) {
                tipoRegistro.value = true
            }
            BotonCambioSeleccion(
                "Salida",
                tipoRegistro.value,
            ){
                tipoRegistro.value = false
            }
        }

        Column(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = CenterHorizontally
        ) {

            if (scholaGateViewModel._idAlumno != -1) {
                mostrarDialog.value = true
            }

            if (mostrarDialog.value){

                selectedAlumno.value = listaAlumnos.find { it.id == scholaGateViewModel._idAlumno }
                selectedGrupo.value = listaGrupos[selectedAlumno.value?.idGrupo]

                if (selectedAlumno.value != null && selectedGrupo.value != null) {
                    MiniCardAlumno(
                        alumno = selectedAlumno.value!!,
                        grupo = selectedGrupo.value!!,
                        isSelected = true,
                    ) {
                        scholaGateViewModel.onValueIdAlumno(-1)
                        mostrarDialog.value = false
                        selectedAlumno.value = null
                        selectedGrupo.value = null
                        mostrarDialog.value = false
                    }
                }

            } else {
                MiniNFCAnimacion(errorAlumno.value)
            }

            TextFieldGenerico(
                value = motivoValue.value,
                onValueChange = {motivoValue.value = it},
                label = "Motivo",
                minLines = 5,
                maxLines = 5,
                isError = errorMotivo.value,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = CenterHorizontally
            ) {
                BotonPrincipal(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterHorizontally),
                    enabled = false,
                    onClick = {
                        Toast.makeText(navController.context, "Añadir Adjunto No Disponible", Toast.LENGTH_SHORT).show()
                    }
                ){
                    Text(
                        text = "Añadir Adjunto",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                BotonPrincipal(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(CenterHorizontally),
                    enabled = true,
                    onClick = {
                        errorMotivo.value = motivoValue.value.isEmpty()
                        errorAlumno.value = selectedAlumno.value == null

                        if (motivoValue.value.isNotEmpty() && selectedAlumno.value != null) {

                            val reporte = ReporteDto(
                                id = 0,
                                idAlumno = selectedAlumno.value!!.id,
                                idUsuario = scholaGateViewModel._usuario.id,
                                tipo = if (tipoRegistro.value) "Entrada" else "Salida",
                                motivo = motivoValue.value
                            )

                            Log.i("Reporte", reporte.toString())

                            scholaGateViewModel.fetchReporte(reporte)

                            if (scholaGateViewModel._reporte.id != 0L) {
                                Toast.makeText(navController.context, "Reporte Guardado", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        }
                    }
                ){
                    Text(
                        text = "Guardar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}