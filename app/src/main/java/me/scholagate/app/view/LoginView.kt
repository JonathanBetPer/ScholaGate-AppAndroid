package me.scholagate.app.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.scholagate.app.StoreCredenciales
import me.scholagate.app.components.TextFieldGenerico
import me.scholagate.app.components.TopBarLogo
import me.scholagate.app.viewModel.ScholaGateViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    navController: NavHostController,
    scholaGateViewModel: ScholaGateViewModel,
    storeCredenciales: StoreCredenciales,
    ){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBarLogo()
        }
    ) {
        ContentLogin(pad = it, scholaGateViewModel,
            storeCredenciales, context, scope, navController)
    }
}

@Composable
fun ContentLogin(
    pad: PaddingValues,
    scholaGateViewModel: ScholaGateViewModel,
    storeCredenciales: StoreCredenciales,
    context: android.content.Context,
    scope: CoroutineScope,
    navController: NavHostController
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val guardarCredenciales = remember { mutableStateOf(false) }

    Column(Modifier.padding(pad)){
        TextFieldGenerico(value = username.value, onValueChange = {username.value = it}, label ="Correo electrónico")
        TextFieldGenerico(value = password.value, onValueChange = {password.value = it}, label ="Contraseña")

        Checkbox(
            checked = guardarCredenciales.value,
            onCheckedChange = {guardarCredenciales.value = it }
        )

        Button(onClick = {

            scholaGateViewModel.fetchLogin(username.value, password.value)

            if (guardarCredenciales.value) {
                scope.launch {
                    storeCredenciales.guardarCredenciales(username.value, password.value)
                }
            }

            if (scholaGateViewModel.tokenCorrecto()){
                Toast.makeText(context, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
            }

        }) {
            Text(text = "Iniciar sesión")
        }
    }

}