package me.scholagate.app.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.scholagate.app.components.BotonPrincipal
import me.scholagate.app.components.CheckBoxLogIn
import me.scholagate.app.components.IconImagotipo
import me.scholagate.app.components.PasswordField
import me.scholagate.app.components.ShowLoading
import me.scholagate.app.components.SpaceV
import me.scholagate.app.components.TextFieldGenerico
import me.scholagate.app.dtos.CredencialesDto
import me.scholagate.app.states.LoginState
import me.scholagate.app.viewModel.ScholaGateViewModel

@Composable
fun LoginView(
    navController: NavHostController,
    scholaGateViewModel: ScholaGateViewModel,
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
                scope,
                guardarCredenciales
            )

            LoginState.Loading -> ShowLoading()

            is LoginState.Error -> {
                ContentLogin(
                    pad = it,
                    scholaGateViewModel,
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
    scope: CoroutineScope,
    guardarCredenciales: MutableState<Boolean>
) {
    val credenciales = scholaGateViewModel.uiAppState.collectAsState().value.credencialesDto

    val username = remember { mutableStateOf(credenciales.nombreUsuario) }
    val password = remember { mutableStateOf(credenciales.password)  }

    Column(
        Modifier
            .padding(pad)
            .padding(20.dp)
            .fillMaxSize(),
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


        BotonPrincipal(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            enabled = true,
            onClick = {
                scholaGateViewModel.fetchLogin(username.value, password.value)

                scope.launch {
                    if (guardarCredenciales.value) {
                        scholaGateViewModel.storeCredenciales.guardarCredenciales(CredencialesDto(username.value, password.value))
                    }
                }
            }

        ){
            Text(
                text = "Iniciar sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarLogo(){
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconImagotipo()
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
        actions = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    )
}