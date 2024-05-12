package me.scholagate.app.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.scholagate.app.R
import me.scholagate.app.components.MainTitle
import me.scholagate.app.viewModel.ScholaGateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValidacionView(navController: NavHostController, scholaGateViewModel: ScholaGateViewModel,){

    Scaffold(
        topBar = {
            TopAppBar(title = { MainTitle(title = "Validación",
                color = MaterialTheme.colorScheme.onPrimary ) },
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
        ContentValidacion(pad = it, scholaGateViewModel = scholaGateViewModel)
    }

}


@Composable
fun ContentValidacion(pad: PaddingValues, scholaGateViewModel: ScholaGateViewModel) {

    val alpha = remember { Animatable(0f) } // Empieza en 0 (completamente transparente)

    LaunchedEffect(key1 = true) {
        while (true) {
            alpha.animateTo(
                targetValue = 0.8f, // Termina en 1 (completamente opaco)
                animationSpec = tween(
                    durationMillis = 2500, // Duración de la animación en milisegundos
                    easing = LinearEasing // Tipo de interpolación
                )
            )
            alpha.animateTo(
                targetValue = 0.15f, // Vuelve a 0 (completamente transparente)
                animationSpec = tween(
                    durationMillis = 2500, // Duración de la animación en milisegundos
                    easing = LinearEasing // Tipo de interpolación
                )
            )
        }
    }

    Column(
        modifier = Modifier.padding(pad).
            padding(50.dp)
            .fillMaxSize()
            .background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
         Icon(
             imageVector = ImageVector.vectorResource(id = R.drawable.nfc_svgrepo_com),
             contentDescription = "Validación NFC",
             tint = Color.White.copy(alpha = alpha.value),
             modifier = Modifier.size(200.dp)
         )
    }

}