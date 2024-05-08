package me.scholagate.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.scholagate.app.view.*
import me.scholagate.app.viewModel.ScholaGateViewModel

@Composable
fun NavManager(scholaGateViewModel: ScholaGateViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login"){
        composable("Login"){
            LoginView(navController)
        }
        composable("Home"){
            HomeView(navController)
        }
        composable("Registro"){
            RegistroView(navController)
        }
        composable("Validacion"){
            ValidacionView(navController)
        }
        composable("WriteNFC"){
            WriteNFCView(navController)
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
