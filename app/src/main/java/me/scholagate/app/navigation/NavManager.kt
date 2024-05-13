package me.scholagate.app.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.scholagate.app.datastore.StoreCredenciales
import me.scholagate.app.components.LoadingApp
import me.scholagate.app.dtos.CredencialesDto
import me.scholagate.app.states.AppState
import me.scholagate.app.view.*
import me.scholagate.app.viewModel.ScholaGateViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun NavManager(scholaGateViewModel: ScholaGateViewModel) {
    val navController = rememberNavController()
    val uiAppState = scholaGateViewModel.uiAppState
    val storeCredenciales = StoreCredenciales(LocalContext.current)


    when (uiAppState.collectAsState().value.appState) {
        AppState.Loading -> {

            LoadingApp()
            val email by storeCredenciales.getEmail.collectAsState("jonathanbetancorperdomo@gmail.com")
            val password by storeCredenciales.getPassword.collectAsState("\"1234!\"")

            scholaGateViewModel.updateAppState(uiAppState.collectAsState().value.copy(
                appState = AppState.Success(true),
                credencialesDto = CredencialesDto(
                    email,
                    password
                )
            ))
            GlobalScope.launch(Dispatchers.Main) {

                scholaGateViewModel.loginStartApp()
            }
        }


        is AppState.Success -> {
            NavHost(navController = navController, startDestination = "Login"){
                composable("Login"){
                    LoginView(navController, scholaGateViewModel, storeCredenciales)
                }
                composable("Home"){
                    scholaGateViewModel.fetchUsuario()
                    HomeView(navController, scholaGateViewModel, storeCredenciales)
                }
                composable("Registro"){
                    RegistroView(navController, scholaGateViewModel)
                }
                composable("Validacion"){
                    ValidacionView(navController, scholaGateViewModel)
                }
                composable("WriteNFC"){
                    WriteNFCView(navController, scholaGateViewModel)
                }
            }
        }
    }
}


/*
 *         composable("ColorPicker/{color}", arguments = listOf(navArgument("color"){ type = NavType.StringType }
 *         )){
 *             val color = it.arguments?.getString("color")?:""
 *             ColorPickerView(qrViewModelState, navController, color)
 *         }
 */
