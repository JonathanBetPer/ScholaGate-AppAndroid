package me.scholagate.app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.scholagate.app.R
import me.scholagate.app.components.AlertDialogPersonalizado
import me.scholagate.app.datastore.StoreCredenciales
import me.scholagate.app.components.BotonPrincipalIcon
import me.scholagate.app.components.CardAlumno
import me.scholagate.app.components.MainTitle
import me.scholagate.app.viewModel.ScholaGateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavHostController,
    scholaGateViewModel: ScholaGateViewModel,
    storeCredenciales: StoreCredenciales,
    ){
    val scope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(title = { MainTitle(title = "Bienvenido ${scholaGateViewModel._usuario.nombre}",
                color = MaterialTheme.colorScheme.onPrimary ) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceTint
                ),
                actions = {
                    Button(
                        onClick = { showDialog.value = true }
                    ){
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                            contentDescription = "Cerrar Sesión")
                    }
                }
            )
        }
    ) {
        CerrarSesion(showDialog, scholaGateViewModel, storeCredenciales, scope, navController)
        ContentHome(pad = it, scholaGateViewModel = scholaGateViewModel, navController = navController)
    }
}


@Composable
fun ContentHome(
    pad: PaddingValues,
    scholaGateViewModel: ScholaGateViewModel,
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(pad),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        BotonPrincipalIcon(
            idIcon = R.drawable.ic_launcher_foreground,
            description = "Crear Reporte",
            ) {
            navController.navigate("Registro")
        }

        BotonPrincipalIcon(
            idIcon = R.drawable.baseline_verified_24,
            description = "Verificar Información NFC"
        ) {
            navController.navigate("Validacion")
        }

        BotonPrincipalIcon(
            idIcon = R.drawable.nfc_svgrepo_com,
            description = "Escribir Información NFC",
            scholaGateViewModel._usuario.rol == "Admin"
        ) {
            navController.navigate("WriteNFC")
        }
    }
}


@Composable
fun CerrarSesion(
    showDialog: MutableState<Boolean>,
    scholaGateViewModel: ScholaGateViewModel,
    storeCredenciales: StoreCredenciales,
    scope: CoroutineScope,
    navController: NavHostController
) {

    if (showDialog.value){
        AlertDialogPersonalizado(
            onDismissRequest = { showDialog.value = false },
            onConfirmation = {
                scholaGateViewModel.logout()
                scope.launch {
                    storeCredenciales.borrarCredenciales()
                    delay(5000L)
                    navController.navigate("Login")
                }
            },
            dialogTitle = "¿Quieres cerrar sesión?"
        )
    }
}