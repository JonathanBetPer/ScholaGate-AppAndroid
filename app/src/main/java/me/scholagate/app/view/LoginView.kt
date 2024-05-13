package me.scholagate.app.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.scholagate.app.StoreCredenciales
import me.scholagate.app.components.CheckBoxLogIn
import me.scholagate.app.components.PasswordField
import me.scholagate.app.components.ShowLoading
import me.scholagate.app.components.SpaceV
import me.scholagate.app.components.TextFieldGenerico
import me.scholagate.app.components.TopBarLogo
import me.scholagate.app.dtos.Credenciales
import me.scholagate.app.states.LoginState
import me.scholagate.app.viewModel.ScholaGateViewModel

@Composable
fun LoginView(
    navController: NavHostController,
    scholaGateViewModel: ScholaGateViewModel,
    storeCredenciales: StoreCredenciales,
    ){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val uiLoginViewState by scholaGateViewModel.uiLoginViewState.collectAsState()

    Scaffold(
        topBar = {
            TopBarLogo()
        }
    ) {
        val guardarCredenciales = remember { mutableStateOf(false) }

        when (uiLoginViewState.loginState) {

            LoginState.None ->  ContentLogin(
                pad = it,
                scholaGateViewModel,
                storeCredenciales,
                scope,
                guardarCredenciales
            )

            LoginState.Loading -> ShowLoading()

            is LoginState.Error -> {
                ContentLogin(
                    pad = it,
                    scholaGateViewModel,
                    storeCredenciales,
                    scope,
                    guardarCredenciales
                )
                Toast.makeText(context, "Inicio de sesión incorrecto", Toast.LENGTH_SHORT).show()
            }

            is LoginState.Success -> {
                if (scholaGateViewModel.uiLoginViewState.collectAsState().value.token != ""){
                    navController.navigate("Home")
                }
            }
        }

    }
}

@Composable
fun ContentLogin(
    pad: PaddingValues,
    scholaGateViewModel: ScholaGateViewModel,
    storeCredenciales: StoreCredenciales,
    scope: CoroutineScope,
    guardarCredenciales: MutableState<Boolean>
) {
    val credenciales = scholaGateViewModel.uiAppState.collectAsState().value.credenciales

    val username = remember { mutableStateOf(credenciales?.nombreUsuario ?:"") }
    val password = remember { mutableStateOf(credenciales?.password?:"")  }

    Column(
        Modifier
            .fillMaxSize()
            .padding(pad)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center

    ){
        TextFieldGenerico(
            value = username.value,
            onValueChange = {username.value = it},
            label ="Correo electrónico",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        SpaceV(15.dp)

        PasswordField(
            password = password.value,
            onValueChange = {password.value = it},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        CheckBoxLogIn(
            checked = guardarCredenciales.value,
            onCheckedChange = {guardarCredenciales.value = it},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        SpaceV(20.dp)

        Button(
            content = {
                Text(text = "Iniciar sesión")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                scholaGateViewModel.fetchLogin(username.value, password.value)

                scope.launch {
                    if (guardarCredenciales.value) {
                        storeCredenciales.guardarCredenciales(Credenciales(username.value, password.value))
                    }
                }
            }
        )
    }
}