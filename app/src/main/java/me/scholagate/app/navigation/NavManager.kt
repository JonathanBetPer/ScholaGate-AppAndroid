package me.scholagate.app.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.scholagate.app.components.LoadingApp
import me.scholagate.app.datastore.StoreCredenciales
import me.scholagate.app.dtos.AlumnoDto
import me.scholagate.app.dtos.CredencialesDto
import me.scholagate.app.states.AppState
import me.scholagate.app.states.NFCState
import me.scholagate.app.view.*
import me.scholagate.app.viewModel.ScholaGateViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun NavManager(
    scholaGateViewModel: ScholaGateViewModel,
    storeCredenciales: StoreCredenciales,
) {
    val navController = rememberNavController()
    val uiAppState = scholaGateViewModel.uiAppState

    when (uiAppState.collectAsState().value.appState) {
        AppState.Loading -> {
            LoadingApp()
        }


        is AppState.Success -> {
            val credencielesExito = (uiAppState.collectAsState().value.appState as AppState.Success).credencielesExito

            val startDestination = if (credencielesExito) {
                "Home"
            } else {
                "Login"
            }

            NavHost(navController = navController, startDestination = startDestination){
                composable("Login"){
                    LoginView(navController, scholaGateViewModel, storeCredenciales)
                }
                composable("Home"){
                    scholaGateViewModel.fetchUsuario()

                    scholaGateViewModel.updateNFCState(
                        scholaGateViewModel.uiNfcViewState.collectAsState().value.copy(
                            NFCState = NFCState.None,
                            alumno = AlumnoDto()
                        )
                    )

                    HomeView(navController, scholaGateViewModel, storeCredenciales)
                }
                composable("Registro"){
                    RegistroView(navController, scholaGateViewModel)
                }
                composable("Validacion"){
                    scholaGateViewModel.fetchGruposInfo()

                    scholaGateViewModel.updateNFCState(
                        scholaGateViewModel.uiNfcViewState.collectAsState().value.copy(
                            NFCState = NFCState.ReadyToRead,
                            alumno = AlumnoDto()
                        )
                    )

                    ValidacionView(navController, scholaGateViewModel)
                }
                composable("WriteNFC"){
                    scholaGateViewModel.fetchAlumnos()
                    scholaGateViewModel.fetchGruposInfo()

                    WriteNFCView(navController, scholaGateViewModel)
                }
            }
        }
    }
}