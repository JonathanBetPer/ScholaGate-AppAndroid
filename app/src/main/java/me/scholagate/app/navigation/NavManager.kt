package me.scholagate.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.scholagate.app.StoreCredenciales
import me.scholagate.app.view.*
import me.scholagate.app.viewModel.ScholaGateViewModel

@Composable
fun NavManager(scholaGateViewModel: ScholaGateViewModel, storeCredenciales: StoreCredenciales) {
    val navController = rememberNavController()

    val startDestination = if (scholaGateViewModel.tokenCorrecto()) "Home" else "Login"

    NavHost(navController = navController, startDestination = startDestination){
        composable("Login"){
            LoginView(navController, scholaGateViewModel, storeCredenciales)
        }
        composable("Home"){

            scholaGateViewModel.fetchUsuario()


            HomeView(navController, scholaGateViewModel)
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


/*
 *         composable("ColorPicker/{color}", arguments = listOf(navArgument("color"){ type = NavType.StringType }
 *         )){
 *             val color = it.arguments?.getString("color")?:""
 *             ColorPickerView(qrViewModelState, navController, color)
 *         }
 */
