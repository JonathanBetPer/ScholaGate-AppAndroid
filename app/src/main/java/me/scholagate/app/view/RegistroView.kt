package me.scholagate.app.view

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.scholagate.app.R
import me.scholagate.app.components.BotonCambioSeleccion
import me.scholagate.app.components.MainTitle
import me.scholagate.app.components.TextFieldGenerico
import me.scholagate.app.ui.theme.SgAzul
import me.scholagate.app.ui.theme.SgNaranja
import me.scholagate.app.viewModel.ScholaGateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroView(navController: NavHostController, scholaGateViewModel: ScholaGateViewModel,){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    MainTitle(
                        title = "Registro",
                        color = MaterialTheme.colorScheme.onPrimary
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
        ContentRegistro(pad = it, scholaGateViewModel = scholaGateViewModel)
    }
}



@Composable
fun ContentRegistro(pad: PaddingValues, scholaGateViewModel: ScholaGateViewModel) {

    val tipoRegistro = remember { mutableStateOf(true) }
    val motivoValue = remember { mutableStateOf("") }

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
                SgNaranja,
                SgAzul
            ) {
                tipoRegistro.value = true
            }
            BotonCambioSeleccion(
                "Salida",
                tipoRegistro.value,
                SgNaranja,
                SgAzul
            ){
                tipoRegistro.value = false
            }
        }

        Column(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = CenterHorizontally
        ) {
            TextFieldGenerico(
                value = motivoValue.value,
                onValueChange = {motivoValue.value = it},
                label = "Motivo",
            )

            Button(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 10.dp),
                onClick = { /*TODO*/ },
                enabled = true,
            ) {
                Text(text = "Guardar")
            }
        }
    }
}